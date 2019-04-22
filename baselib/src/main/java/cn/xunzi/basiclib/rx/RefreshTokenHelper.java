package cn.xunzi.basiclib.rx;

import cn.xunzi.basiclib.exception.ExceptionHandle;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author xunzi Android Develoer
 * @since 2018/7/18
 */

public class RefreshTokenHelper {

    public static <T> Function<Throwable, ObservableSource<T>> refreshTokenFunc(final Observable<T> observable) {
        return new Function<Throwable, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(Throwable throwable) throws Exception {
               /* if (throwable instanceof HttpException) {
                    HttpException httpException = (HttpException) throwable;
                    if (HttpStatusCode.UNAUTHORIZED == httpException.code()) {
                        return UserServiceFactory.updateToken(XZUserManager.getUserToken().getRefreshToken())
                                .observeOn(Schedulers.io())
                                .flatMap(tokenBean -> {
                                    XZUserManager.updateUserToken(tokenBean);
                                    return observable;

                                });
                    }
                }*/
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        };
    }


}
