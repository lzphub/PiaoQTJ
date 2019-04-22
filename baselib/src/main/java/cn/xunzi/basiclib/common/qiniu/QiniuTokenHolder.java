package cn.xunzi.basiclib.common.qiniu;


import cn.xunzi.basiclib.base.BaseView;

/**
 * description: 七牛Token 和域名单例
 *
 * @author vane
 * @since 2018/3/26
 */

public class QiniuTokenHolder {

    volatile private static QiniuTokenHolder instance = null;

    private static QiniuConfigResponse mQiniuConfigResponse;

    private QiniuTokenHolder() {
    }

    public static QiniuTokenHolder getInstance() {
        if (instance == null) {
            synchronized (QiniuTokenHolder.class) {
                if (instance == null) {
                    instance = new QiniuTokenHolder();
                }
            }
        }
        return instance;
    }

    public void init(BaseView view, final QiniuTokenCallBack callBack) {
        if (mQiniuConfigResponse != null) {
            if (callBack != null) {
                callBack.obtainSuccess();
            }
            return;
        }
        /*CommonApiFactory.obtainQiniuConfig().subscribe(new AbstractDialogSubscriber<QiniuConfigResponse>(view) {
            @Override
            public void onNext(QiniuConfigResponse qiniuTokenResponse) {
                mQiniuConfigResponse = qiniuTokenResponse;
                if (callBack != null) {
                    callBack.obtainSuccess();
                }
            }
        });*/
    }

    public interface QiniuTokenCallBack {
        /**
         * 获取七牛Token域名成功
         */
        void obtainSuccess();
    }

    public QiniuConfigResponse getQiniuTokenResponse() {
        return mQiniuConfigResponse == null ? new QiniuConfigResponse() : mQiniuConfigResponse;
    }

}
