package cn.dankal.basiclib.api;

import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.base.BaseResult;
import cn.dankal.basiclib.pojo.CheckCode;
import cn.dankal.basiclib.pojo.UserInfoBean;
import cn.dankal.basiclib.pojo.UserResponseBody;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Date: 2018/7/27.
 * Time: 17:44
 * classDescription:
 * 用户
 *
 * @author fred
 */

@ApiFactory(value = BaseApi.class)
public interface UserService {

    /**
     * 获取验证码 scalar
     * 找回密码:pass 设置支付密码: set_pay 绑定银行卡 bindcard 注册：sign_up
     */
    @GET("customer/login/getcode")
    Observable<String> obtainVerifyCode(@Query("email") String moblie, @Query("type") String type);

    /**
     * 工程师端获取验证码
     */
    @GET("engineer/login/getcode")
    Observable<String> getCode(@Query("email") String moblie, @Query("type") String type);

    /**
     * 检查验证码 key
     */
    @POST("engineer/login/checkcode")
    @FormUrlEncoded
    Observable<CheckCode> verifyCode(@Field("email") String email,
                                     @Field("type") String type,
                                     @Field("code") String code);

    /**
     * 设置密码 scalar
     */
    @POST("customer/login/signUp")
    @FormUrlEncoded
    Observable<String> resetPassword(@Field("email") String email,  @Field("password") String password);

    /**
     * 工程师端注册
     */
    @POST("engineer/login/signUp")
    @FormUrlEncoded
    Observable<String> engResetPassword(@Field("email") String email,  @Field("password") String password);
    /**
     * 修改密码
     */

    @POST("customer/login/changePwd")
    @FormUrlEncoded
    Observable<String> changePwd(@Field("email") String emasil,@Field("password")String password);

    /**
     * 工程师端修改密码
     */
    @POST("engineer/login/changePwd")
    @FormUrlEncoded
    Observable<String> engChangePwd(@Field("email") String emasil,@Field("password")String password);


    /**
     * 登录
     */
    @POST("customer/login/login")
    @FormUrlEncoded
    Observable<UserResponseBody> login(@Field("email") String email, @Field("password") String password);

    /**
     * 工程师端登录
     */
    @POST("engineer/login/login")
    @FormUrlEncoded
    Observable<UserResponseBody> engineerLogin(@Field("email") String email, @Field("password") String password);
    /**
     * 更新TOKEN
     */
    @POST("customer/login/refreshtoken")
    @FormUrlEncoded
    Observable<UserResponseBody.TokenBean> refreshtoken(@Field("refresh_token")String refresh_token);

    /**
     * 工程师端更新TOKEN
     */
    @POST("engineer/login/refreshtoken")
    @FormUrlEncoded
    Observable<UserResponseBody.TokenBean> engineerRefreshtoken(@Field("refresh_token")String refresh_token);


    /**
     * 修改头像
     * @param avatar 头像相对地址
     * @return
     */
    @POST("customer/me/updateinfo")
    @FormUrlEncoded
    Observable<String> updateAvatar(@Field("avatar") String avatar);



}
