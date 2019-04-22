package cn.xunzi.basiclib.api;

import cn.xunzi.annotations.ApiFactory;
import cn.xunzi.basiclib.base.BaseResult;
import cn.xunzi.basiclib.bean.LoginBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.RegisterBean;
import cn.xunzi.basiclib.pojo.CheckCode;
import cn.xunzi.basiclib.pojo.UserInfoBean;
import cn.xunzi.basiclib.pojo.UserResponseBody;
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

    //注册
    @POST("user/info/userRegister")
    @FormUrlEncoded
    Observable<RegisterBean> redister(@Field("tel")String tel,@Field("code")String code,@Field("password")String password,@Field("invateCode")String invateCode);

    //登录
    @POST("user/info/userLoginByTel")
    @FormUrlEncoded
    Observable<UserResponseBody> login(@Field("tel")String tel, @Field("password")String password);

    //忘记密码
    @POST("user/info/forgetPassword")
    @FormUrlEncoded
    Observable<PostBean> forpwd(@Field("tel")String tel, @Field("code")String code, @Field("password")String password);

    //获取验证码
    @POST("sms/getMsg")
    @FormUrlEncoded
    Observable<PostBean> getCode(@Field("phone")String phone,@Field("types")String types);
}
