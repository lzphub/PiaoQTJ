package cn.xunzi.basiclib.common.qiniu;

import cn.xunzi.basiclib.api.BaseApi;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fred on 2016/11/29.
 */

public class QiniuApi {
    private static QiniuApi mInstance;
    private QiniuService mService;
    //七牛的url
    public static final String  BASE_QN_URL= BaseApi.BASE_URL;

    private QiniuApi(){
        mService= BaseApi.getRetrofit(BASE_QN_URL).create(QiniuService.class);
    }

    public static QiniuApi getInstance(){
         if (mInstance==null){
             synchronized (QiniuApi.class){
                 if (mInstance==null){
                     mInstance=new QiniuApi();
                 }
             }
         }
        return mInstance;
    }


    /**
     * 获取七牛Token,七牛域名
     * @return
     */
    public Observable<QiniuConfigResponse> getQN(){
        return mService.getQiniu().subscribeOn(Schedulers.io());
    }

}
