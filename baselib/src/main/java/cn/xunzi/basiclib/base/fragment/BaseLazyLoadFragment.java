package cn.xunzi.basiclib.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * description: 懒加载Fragment
 *
 * @author xunzi Android Developer
 * @since 2018/2/6
 */

public abstract class BaseLazyLoadFragment extends BaseStateFragment {

    /**
     * 当前Fragment是否可见
     */
    protected boolean isVisible = false;
    /**
     * 是否与View建立起映射关系
     */
    protected boolean isInitView = false;
    /**
     * 是否是第一次加载数据
     */
    protected boolean isFirstLoad = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isInitView = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initComponents();
        lazyLoadData();
    }


    //当fragment结合viewpager使用的时候 这个方法会调用
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected void lazyLoadData() {
        if (isFirstLoad && isVisible && isInitView) {
            isFirstLoad = false;
            obtainData();
        }
    }

    //当fragment没有结合viewpager使用的时候 isVisible永远为false
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        checkLoad();
    }

    /**
     * 手动判断是否需要更新数据
     */
    protected void checkLoad() {
        if (isVisible && isInitView) {
            isFirstLoad = false;
            obtainData();
        }
    }

}
