package cn.dankal.basiclib.api;



import java.util.List;

import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.MyWorkDataBean;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
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
import retrofit2.http.Path;
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
    Observable<ProductHomeListBean> getProduct(@Query("page_index")int page_index, @Query(" page_size")int  page_size);

    /**
     * 用户端轮播图
     */
    @GET("customer/home/getcarousel")
    Observable<UserHomeBannerBean> getBanner();

    /**
     * 工程师端轮播图
     */
    @GET("engineer/home/getcarousel")
    Observable<UserHomeBannerBean> getEngBanner();

    /**
     * 首页需求
     */
    @GET("engineer/home/getproject")
    Observable<DemandListbean> getDemandList(@Query("page_index")int page_index,@Query(" page_size")int  page_size);


    /**
     * 工单列表
     */
    @GET("engineer/me/getprojectlist")
    Observable<MyWorkListBean> getWorkList(@Query("page_index")String page_index, @Query("page_size")String page_size, @Query("status")String status);

    /**
     * 工单详情
     */
    @GET("engineer/me/getprojectdetail/{project_uuid}")
    Observable<MyWorkDataBean> getWorkData(@Path("project_uuid")String project_uuid);

    /**
     * 认领需求
     */
    @POST("engineer/home/postplan")
    @FormUrlEncoded
    Observable<String> postplan(@Field("project_uuid")String project_uuid, @Field("plan_detail")String plan_detail, @Field("plan_images[]")List<String> plan_images);

    /**
     *  发布创意
     */
    @POST("engineer/home/postidea")
    @FormUrlEncoded
    Observable<String> postidea(@Field("title")String title,@Field("detail")String detail,@Field("images[]")List<String> iamges);
}
