package cn.xunzi.basiclib.base.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.BaseView;
import cn.xunzi.basiclib.util.ActivityUtils;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
import cn.xunzi.basiclib.util.StatusBarHelper;
import cn.xunzi.basiclib.util.TitleBarUtils;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.widget.TipDialog;
import cn.xunzi.basiclib.widget.titlebar.ITitleBar;
import cn.xunzi.basiclib.widget.titlebar.SingleTextTitle;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description: Fragment 基类
 *
 * @author xunzi Android Developer
 * @since 2018/2/6
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected View mContentView;
    protected TipDialog loadingDialog;
    protected Unbinder unbinder;

    private CompositeDisposable mDisposables = new CompositeDisposable();
    protected boolean isVisibleToUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initComponents(view);
        obtainData();
    }


    /**
     * 设置状态栏占位条
     */
    public void setStatusbarHolder(View statusbarHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup.LayoutParams layoutParams = statusbarHolder.getLayoutParams();
            layoutParams.height = StatusBarHelper.getStatusbarHeight(getContext());
            statusbarHolder.setLayoutParams(layoutParams);
        }
    }


    /**
     * 添加标题栏
     */
    public void addTitleBar(ITitleBar iTitleBar) {
        if (iTitleBar == null) return;

        int titleBarResId = iTitleBar.getViewResId();

        View toolbarContainer = TitleBarUtils.init(getActivity(), titleBarResId);

        iTitleBar.onBindTitleBar(toolbarContainer);
    }

    public void addSindleTitleBar(String title) {
        addTitleBar(new SingleTextTitle(title));
    }

    /**
     * 一般用于加载网络请求
     * 此方法不是抽象方法，通过覆盖实现，可调用多次
     */
    public void obtainData() {
    }

    /**
     * onCreateView layout id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
    protected abstract void initComponents();
    protected abstract void initComponents(View view);


    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if("user".equals(SharedPreferencesUtils.getString(getContext(),"identity","user"))){
            loadingDialog = new TipDialog.Builder(getContext())
                    .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("Loading")
                    .create();
        }else{
            loadingDialog = new TipDialog.Builder(getContext())
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
        ToastUtils.showShort(message);
    }

    @Override
    public void addNetworkRequest(Disposable d) {
        mDisposables.add(d);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposables != null) mDisposables.clear();
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void tokenInvalid() {
        XZUserManager.resetToken();
        XZUserManager.resetUserInfo();
//        EMClient.getInstance().logout(true);
        ActivityUtils.finishAllActivities();
        try {
            startActivity(new Intent(getContext(),
                    Class.forName(getString(R.string.login_activity_path))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isVisibleToUser=!hidden;
    }
}
