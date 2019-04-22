package cn.xunzi.basiclib.rx;

import cn.xunzi.basiclib.base.BaseView;
import io.reactivex.disposables.Disposable;

/**
 * @author xunzi Android Developer
 * @since 2018/7/18
 */

public abstract class AbstractDialogSubscriber<T> extends AbstractSubscriber<T> {

    public AbstractDialogSubscriber(BaseView view) {
        super(view);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (view != null) view.dismissLoadingDialog();
    }


    @Override
    public void onComplete() {
        if (view != null) view.dismissLoadingDialog();
        super.onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (view != null) view.showLoadingDialog();
    }

}
