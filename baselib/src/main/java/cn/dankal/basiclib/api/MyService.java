package cn.dankal.basiclib.api;


import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.bean.AboutUsBean;
import cn.dankal.basiclib.bean.AddressBean;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@ApiFactory(value = BaseApi.class)
public interface MyService {
    /**
     * 关于我们
     */
    @POST("customer/me/getaboutus")
    Observable<AboutUsBean> getAboutUs();

    /**
     * 工程师端关于我们
     */
    @POST("engineer/me/getaboutus")
    Observable<AboutUsBean> engGetAboutus();

    /**
     * 常见问题
     */
    @GET("customer/me/getFaqList")
    Observable<ComProbBean> getUserFaqList(@Query("page_index")int page_index,@Query(" page_size")int  page_size);

    /**
     * 意见反馈
     */
    @POST("customer/me/postfeedback")
    @FormUrlEncoded
    Observable<String> postFeedBack(@Field("content")String content, @Field("images")String[] images);

    /**
     * 用户个人信息
     */
    @GET("customer/me")
    Observable<PersonalData_EnBean> getUserData();

    /**
     * 工程师个人信息
     */
    @GET("engineer/me")
    Observable<PersonalData_EngineerBean> getEngineerData();

    /**
     * 编辑个人信息
     */
    @Headers({"Content-Type: application/json"})
    @PUT("customer/me/updateinfo")
    Observable<String> updateInfo(@Body PersonalData_EnBean personalData_enBean);

    /**
     * 工程师编辑个人信息
     */
    @Headers({"Content-Type: application/json"})
    @PUT("engineer/me/updateinfo")
    Observable<String> engineerUpdateInfo(@Body PersonalData_EngineerPostBean personalData_engineerPostBean);


    /**
     * 我的意向
     */
    @POST("customer/me/getIntentionList")
    @FormUrlEncoded
    Observable<MyIntentionBean> getIntentionList(@Field("status")String status);

    /**
     * 意向详情
     */
    @GET("customer/me/getIntentionDetail/{intention_id}")
    Observable<IntentionDateBean> getIntentionInfo(@Path("intention_id")String intention_id);

    /**
     * 我的收藏
     */
    @GET("customer/me/getFavouriteList")
    Observable<ProductListBean> getFavouriteList(@Query("page_index")int page_index,@Query(" page_size")int  page_size);

    /**
     * 需求列表
     */
    @GET("customer/demand")
    Observable<MyRequestBean> getMyRequest(@Query("page_index")int page_index,@Query(" page_size")int  page_size);

    /**
     * 删除需求
     */
    @DELETE("customer/demand/{demand_id}")
    Observable<String> deleteMyRequest(@Path("demand_id") String demand_id);

    /**
     * 需求详情
     */
    @GET("customer/demand/{demand_id}")
    Observable<RequestDataBean> getMyRequestData(@Path("demand_id")String demand_id);

    /**
     * 地理信息
     */
    @GET("common/getGeoInfo")
    Observable<AddressBean> getGeoInfo();
}
