package cn.dankal.basiclib.api;


import java.util.List;

import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.bean.AboutUsBean;
import cn.dankal.basiclib.bean.AddressBean;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.bean.MyEarBean;
import cn.dankal.basiclib.bean.MyIdeaListBean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.NewServiceMsgBean;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import cn.dankal.basiclib.bean.SystemMsgBean;
import cn.dankal.basiclib.bean.TransactionBean;
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
    Observable<ComProbBean> getUserFaqList(@Query("page_index") int page_index, @Query(" page_size") int page_size);

    /**
     *工程师端常见问题
     */
    @GET("engineer/me/getFaqList")
    Observable<ComProbBean> getEngFaqList(@Query("page_index") int page_index, @Query(" page_size") int page_size);

    /**
     * 意见反馈
     */
    @POST("customer/me/postfeedback")
    @FormUrlEncoded
    Observable<String> postFeedBack(@Field("content") String content, @Field("images[]") List<String> images);

    /**
     * 工程师端意见反馈
     */
    @POST("engineer/me/postfeedback")
    @FormUrlEncoded
    Observable<String> engPostFeedBack(@Field("content") String content, @Field("images[]") List<String> images);

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
    Observable<MyIntentionBean> getIntentionList(@Field("status") String status,@Field("page_index") int page_index, @Field(" page_size") int page_size);

    /**
     * 意向详情
     */
    @GET("customer/me/getIntentionDetail/{intention_id}")
    Observable<IntentionDateBean> getIntentionInfo(@Path("intention_id") String intention_id);

    /**
     * 我的收藏
     */
    @GET("customer/me/getFavouriteList")
    Observable<ProductListBean> getFavouriteList(@Query("page_index") int page_index, @Query(" page_size") int page_size);

    /**
     * 需求列表
     */
    @GET("customer/demand")
    Observable<MyRequestBean> getMyRequest(@Query("status")String status,@Query("page_index") int page_index, @Query(" page_size") int page_size);

    /**
     * 删除需求
     */
    @DELETE("customer/demand/{demand_id}")
    Observable<String> deleteMyRequest(@Path("demand_id") String demand_id);

    /**
     * 需求详情
     */
    @GET("customer/demand/{demand_id}")
    Observable<RequestDataBean> getMyRequestData(@Path("demand_id") String demand_id);

    /**
     * 地理信息
     */
    @GET("common/getGeoInfo")
    Observable<AddressBean> getGeoInfo();

    /**
     * 我的收益
     */
    @GET("engineer/myprofit")
    Observable<MyEarBean> getMyEar();

    /**
     * 绑定银行卡获取验证码
     */
    @GET("engineer/myprofit/getcode")
    Observable<String> bankCardCode(@Query("mobile") String phone);

    /**
     * 绑定银行卡
     */
    @POST("engineer/myprofit/addbankcard")
    @FormUrlEncoded
    Observable<String> bindBankCard(@Field("card_number") String card_number, @Field("cardholder") String cardholder, @Field("id_card_number") String id_card_number, @Field("reserved_mobile") String reserved_mobile, @Field("bank_name") String bank_name, @Field("code") String code);

    /**
     * 银行卡列表
     */
    @GET("engineer/myprofit/getbankcardlist")
    Observable<BankCardListBean> getBankCardList();

    /**
     * 删除银行卡
     */
    @DELETE("engineer/myprofit/deletebankcard/{bank_card_number}")
    Observable<String> deletBankCard(@Path("bank_card_number") String bank_card_number);

    /**
     * 设置支付密码
     */
    @POST("engineer/me/setwithdrawalpwd")
    @FormUrlEncoded
    Observable<String> setWithPwd(@Field("withdrawal_password") String withdrawal_password);

    /**
     * 提现
     */
    @POST("engineer/myprofit/withdrawal")
    @FormUrlEncoded
    Observable<String> withdrawal(@Field("withdrawal_password") String withdrawal_password, @Field("withdrawal_money") String withdrawal_money, @Field("bank_card_number") String bank_card_number);

    /**
     * 交易记录
     */
    @GET("engineer/myprofit/gettransactionlist")
    Observable<TransactionBean> getTransactionList(@Query("page_index") int page_index, @Query("page_size") int page_size);

    /**
     *  我的创意
     */
    @GET("engineer/me/getidealist")
    Observable<MyIdeaListBean> getMyIdeaList(@Query("status")int status,@Query("page_index") int page_index, @Query("page_size") int page_size);

    /**
     * 工程师端发送消息
     */
    @POST("engineer/service")
    @FormUrlEncoded
    Observable<String> serviceSendMsg(@Field("content")String content,@Field("type")int type);

    /**
     * 工程师端获取聊天记录
     */
    @GET("engineer/service")
    Observable<ChatBean> getMsgRecord(@Query("page_index") String page_index,@Query("page_size")String page_size);

    /**
     *  工程师端获取最新消息
     */
    @GET("engineer/Service/getNew")
    Observable<NewServiceMsgBean> getEngNewServiceMsg();

    /**
     *  用户端发送消息
     */
    @POST("customer/service")
    @FormUrlEncoded
    Observable<String> userServiceSendMsg(@Field("content")String content,@Field("type")int type);

    /**
     * 用户端获取聊天记录
     */
    @GET("customer/service")
    Observable<ChatBean> getUserMsgRecord(@Query("page_index")String page_index,@Query("page_size")String page_size);

    /**
     *  用户端获取最新消息
     */
    @GET("customer/Service/getNew")
    Observable<NewServiceMsgBean> getNewServiceMsg();

    /**
     * 用户端系统消息列表
     */
    @POST("customer/me/getmessagelist")
    @FormUrlEncoded
    Observable<SystemMsgBean> getSystemMsg(@Field("page_index")int page_index,@Field("page_size")int page_size);

    /**
     * 工程师端系统消息列表
     */
    @POST("engineer/me/getmessagelist")
    @FormUrlEncoded
    Observable<SystemMsgBean> engineerGetSystemMsg(@Field("page_index")int page_index,@Field("page_size")int page_size);

}
