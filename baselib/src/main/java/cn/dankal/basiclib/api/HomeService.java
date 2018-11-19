package cn.dankal.basiclib.api;



import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import io.reactivex.Observable;

import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.bean.PostRequestBean;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

@ApiFactory(value = BaseApi.class)
public interface HomeService {

    /**
     * 发布需求
     */
    @POST("customer/demand")
    Observable<String> postRequest(@Body PostRequestBean postRequestBean);

    /**
     * 产品分类
     */
    @GET("customer/product/getCatTree")
    Observable<ProductClassifyBean> getCatTree();

    /**
     *最新产品
     */
    @GET("customer/home/getproduct")
    Observable<ProductListBean> getProduct(@Query("page_index")int page_index,@Query(" page_size")int  page_size);

    /**
     * 用户端轮播图
     */
    @GET("customer/home/getcarousel")
    Observable<UserHomeBannerBean> getBanner();
}
