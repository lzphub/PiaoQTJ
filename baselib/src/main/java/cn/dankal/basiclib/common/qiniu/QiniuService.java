package cn.dankal.basiclib.common.qiniu;

import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.api.BaseApi;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Fred on 2016/11/29.
 */


public interface QiniuService  {
    /**
     * 获取七牛Token和域名
     * @return
     */

    @GET("Communal/qiniu")
    Observable<QiniuConfigResponse> getQiniu();
}
