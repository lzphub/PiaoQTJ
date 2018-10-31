package cn.dankal.basiclib.base.fragment;

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
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.StatusBarHelper;
import cn.dankal.basiclib.util.TitleBarUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.basiclib.widget.titlebar.ITitleBar;
import cn.dankal.basiclib.widget.titlebar.SingleTextTitle;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description: Fragment 基类
 *
 * @author Dankal Android Developer
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
        loadingDialog = new TipDialog.Builder(getActivity())
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
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
        DKUserManager.resetToken();
        DKUserManager.resetUserInfo();
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
