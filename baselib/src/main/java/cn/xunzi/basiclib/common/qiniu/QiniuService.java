package cn.xunzi.basiclib.common.qiniu;

import cn.xunzi.annotations.ApiFactory;
import cn.xunzi.basiclib.api.BaseApi;
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
