package cn.xunzi.basiclib.base;

import android.support.annotation.NonNull;

/**
 * description: Presenter 基类
 *
 * @author vane
 * @since 2018/2/1
 */

public interface BasePresenter<T extends BaseView> {
    /**
     * View 启动时
     *
     * @param view class extends BaseView
     */
    void attachView(@NonNull T view);

    /**
     * View 销毁时
     */
    void detachView();
}
