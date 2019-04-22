package cn.xunzi.basiclib.rx;

import android.util.Log;

import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.BaseView;
import cn.xunzi.basiclib.domain.HttpStatusCode;
import cn.xunzi.basiclib.exception.LocalException;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
import cn.xunzi.basiclib.util.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xunzi Android Developer
 * @since 2018/7/18
 */

public abstract class AbstractSubscriber<T> implements Observer<T> {

    protected BaseView view;
    private String type;

    public AbstractSubscriber(BaseView view) {
        this.view = view;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (view != null) view.addNetworkRequest(d);
    }

    @Override
    public void onError(Throwable e) {
        if (e == null || view == null) return;
        if (e instanceof LocalException) {
            LocalException exception = (LocalException) e;
            //401 重新获取access token , 如果还返回412 就是refresh token 也失效了。需要重新登录
            if (exception.getErrorCode() == HttpStatusCode.TOKEN_INVAILD||
                    exception.getErrorCode() == HttpStatusCode.UNAUTHORIZED) {
                view.tokenInvalid();
                ToastUtils.showShort(R.string.loginfailure);
            } else {
                view.showToast(exception.getMsg());
            }
        } else {
            Log.e("SubscriberThrowable", e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        this.view = null;
    }
}
