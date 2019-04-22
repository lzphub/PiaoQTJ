package cn.xunzi.basiclib.rx;

import cn.xunzi.basiclib.base.BaseStateView;

/**
 * @author xunzi Android Developer
 * @since 2018/7/18
 */

public abstract class AbstractStateSubscriber<T> extends AbstractSubscriber<T> {

    protected BaseStateView stateView;

    public AbstractStateSubscriber(BaseStateView view) {
        super(view);
        stateView = view;
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (stateView != null) stateView.showRetry();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        stateView = null;
    }

    @Override
    public void onNext(T t) {
        if (stateView != null) stateView.showContent();
    }
}
