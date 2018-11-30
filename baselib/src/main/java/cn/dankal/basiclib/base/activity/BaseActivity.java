package cn.dankal.basiclib.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import butterknife.ButterKnife;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.DKHandler;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.TitleBarUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.basiclib.widget.statubar.QMUIStatusBarHelper;
import cn.dankal.basiclib.widget.titlebar.ITitleBar;
import cn.dankal.basiclib.widget.titlebar.SingleTextTitle;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Dankal Android Developer
 * @since 2018/7/3
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected TipDialog loadingDialog;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());

            //ButterKnife绑定
            ButterKnife.bind(this);

            initStatusBar();
            //初始化组件
            initComponents();

            obtainData();
        }
    }
    protected void initStatusBar() {
        TitleBarUtils.compat(this, getResources().getColor(R.color.color00));
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }


    /**
     * setContentView(LayoutResId)
     *
     * @return LayoutResId
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
    protected abstract void initComponents();

    /**
     * 添加标题栏
     */
    public void addTitleBar(ITitleBar iTitleBar) {
        if (iTitleBar == null) return;

        int titleBarResId = iTitleBar.getViewResId();

        View toolbarContainer = TitleBarUtils.init(this, titleBarResId);

        toolbarContainer.findViewById(R.id.iv_onback)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        iTitleBar.onBindTitleBar(toolbarContainer);
    }

    public void addSindleTitleBar(String title) {
        addTitleBar(new SingleTextTitle(title));
    }

    public void obtainData() {}

    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if("user".equals(SharedPreferencesUtils.getString(this,"identity","user"))){
            loadingDialog = new TipDialog.Builder(this)
                    .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("Loading")
                    .create();
        }else{
            loadingDialog = new TipDialog.Builder(this)
                    .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }

        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        try {
            ToastUtils.showShort(message);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void tokenInvalid() {
//        EMClient.getInstance().logout(true);
        DKUserManager.resetToken();
        DKUserManager.resetUserInfo();
        ActivityUtils.finishAllActivities();
        try {
            startActivity(new Intent(this,
                    Class.forName(getString(R.string.login_activity_path))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addNetworkRequest(Disposable d) {
        mDisposables.add(d);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) mDisposables.clear();
    }

    @Override
    protected void onPause() {
        ToastUtils.cancel();
        super.onPause();
    }

    protected void postDelayedToFinish() {
        new DKHandler(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
