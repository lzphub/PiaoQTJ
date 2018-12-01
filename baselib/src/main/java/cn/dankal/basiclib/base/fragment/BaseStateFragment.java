package cn.dankal.basiclib.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.dankal.basiclib.base.BaseStateView;
import cn.dankal.basiclib.widget.loadsir.EmptyCallback;
import cn.dankal.basiclib.widget.loadsir.EmptyEnCallback;
import cn.dankal.basiclib.widget.loadsir.LoadingCallback;
import cn.dankal.basiclib.widget.loadsir.RetryCallback;
import cn.dankal.basiclib.widget.loadsir.callback.Callback;
import cn.dankal.basiclib.widget.loadsir.core.LoadService;
import cn.dankal.basiclib.widget.loadsir.core.LoadSir;

/**
 * description: 包含状态加载图的Fragment基类
 *
 * @author vane
 * @since 2018/2/6
 */

public abstract class BaseStateFragment extends BaseFragment implements BaseStateView {

    public LoadService loadService;

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        obtainData();
    }

    public void initLoadServer(){
        Object targetView;
        if (contentView() instanceof Fragment) {
            targetView = mContentView;
        } else {
            targetView = contentView();
        }
        loadService = LoadSir.getDefault().register(targetView, (Callback.OnReloadListener) v -> {
//            loadService.showCallback(LoadingCallback.class);
//            obtainData();
        });
    }

    @Override
    public void showRetry() {
        if (loadService != null)
            loadService.showCallback(RetryCallback.class);
    }

    @Override
    public void showContent() {
        if (loadService != null)
            loadService.showSuccess();
    }

    @Override
    public void showEmpty() {
        if (loadService != null)
            loadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showLoading() {
        if (loadService != null)
            loadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showEnEmpty() {
        if (loadService != null)
            loadService.showCallback(EmptyEnCallback.class);
    }
}
