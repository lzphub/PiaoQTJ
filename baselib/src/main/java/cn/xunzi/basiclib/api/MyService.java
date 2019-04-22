package cn.xunzi.basiclib.api;


import java.util.List;

import cn.xunzi.annotations.ApiFactory;
import cn.xunzi.basiclib.bean.BankListBean;
import cn.xunzi.basiclib.bean.BanlanceBean;
import cn.xunzi.basiclib.bean.ChangePwdBean;
import cn.xunzi.basiclib.bean.CityBean;
import cn.xunzi.basiclib.bean.MyTeamBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.WithDetaBean;
import cn.xunzi.basiclib.pojo.UserInfoBean;
import cn.xunzi.basiclib.pojo.UserResponseBody;
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
    //银行卡列表
    @POST("user/account/userBank")
    @FormUrlEncoded
    Observable<BankListBean> bankCardList(@Field("userId")String userId);

    //删除银行卡
    @POST("user/account/delete")
    @FormUrlEncoded
    Observable<PostBean> deleteBankCard(@Field("userId")String userId,@Field("bankId")String bankId);

    //绑定银行卡
    @POST("user/account/bind")
    @FormUrlEncoded
    Observable<PostBean> bindCard(@Field("userId")String userId,@Field("name")String name,@Field("province")String province,@Field("city")String city,@Field("bankAddr")String bankAddr,@Field("bankName")String bankName,@Field("bankNo")String bankNo);

    //我的团队
    @POST("user/account/team")
    @FormUrlEncoded
    Observable<MyTeamBean> myTeam(@Field("userId")String userId);

    //修改用户名
    @POST("user/info/editUserInfo")
    @FormUrlEncoded
    Observable<PostBean> editUserName(@Field("uid")String uid,@Field("keys")String keys,@Field("values")String values);

    //用户信息
    @POST("user/info/detail")
    @FormUrlEncoded
    Observable<UserInfoBean> userDeta(@Field("userId")String userId);

    //账户余额
    @POST("user/account/balance")
    @FormUrlEncoded
    Observable<BanlanceBean> getBanlance(@Field("userId")String userId);

    //申请提现
    @POST("user/account/withdraw")
    @FormUrlEncoded
    Observable<PostBean> withBanlance(@Field("userId")String suerId,@Field("amount")String amount,@Field("bankId")String bankId,@Field("payCode")String payCode);

    //提现记录
    @POST("user/account/listRecorde")
    @FormUrlEncoded
    Observable<WithDetaBean> withRecorde(@Field("userId")String userId,@Field("state")String state);

    //修改提现密码
    @POST("user/info/updatePayCode")
    @FormUrlEncoded
    Observable<PostBean> updatePayCode(@Field("uid")String uid,@Field("payCode")String payCode,@Field("smsCode")String smsCode);

    //城市列表
    @POST("index/pcaList")
    Observable<CityBean> getCity();

    //修改登录密码
    @POST("user/info/updatePassword")
    @FormUrlEncoded
    Observable<ChangePwdBean> updatePassword(@Field("userId")String userId, @Field("oldPwd")String oldPwd, @Field("newPwd")String newPwd);
}
