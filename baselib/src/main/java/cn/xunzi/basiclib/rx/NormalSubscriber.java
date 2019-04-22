package cn.xunzi.basiclib.rx;

import android.util.Log;

//import com.hyphenate.chat.EMClient;

import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.XunziApplication;
import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.domain.HttpStatusCode;
import cn.xunzi.basiclib.exception.LocalException;
import cn.xunzi.basiclib.util.ActivityUtils;
import io.reactivex.Observer;

/**
 * @author xunzi Android Developer
 * @since 2018/7/18
 */

public abstract class NormalSubscriber<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof LocalException) {
            LocalException exception = (LocalException) e;
            //401 重新获取access token , 如果还返回412 就是refresh token 也失效了。需要重新登录
            if (exception.getErrorCode() == HttpStatusCode.TOKEN_INVAILD
                    ||exception.getErrorCode() == HttpStatusCode.UNAUTHORIZED) {
//                EMClient.getInstance().logout(true);
                XZUserManager.resetToken();
                XZUserManager.resetUserInfo();
                ActivityUtils.finishAllActivities();
                try {
                    ActivityUtils.startActivity(Class.forName(XunziApplication.getContext().getString(R.string.login_activity_path)));
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            } else {
            }
        } else {
            Log.e("SubscriberThrowable", e.getMessage());
        }
    }

    @Override
    public void onComplete() {
    }
}
