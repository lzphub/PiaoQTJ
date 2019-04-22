package cn.xunzi.basiclib.api;


import java.util.List;

import cn.xunzi.basiclib.bean.AddressListBean;
import cn.xunzi.basiclib.bean.DeleteAddressBean;
import cn.xunzi.basiclib.bean.GetVersionBean;
import cn.xunzi.basiclib.bean.HomeBannerBean;
import cn.xunzi.basiclib.bean.LinkBean;
import cn.xunzi.basiclib.bean.MessageBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.SystemMsgBean;
import io.reactivex.Observable;

import cn.xunzi.annotations.ApiFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

@ApiFactory(value = BaseApi.class)
public interface HomeService {

    //客服联系方式
    @POST("index/link")
    Observable<LinkBean> link();

    //轮播图
    @POST("index/banner")
    @FormUrlEncoded
    Observable<HomeBannerBean> getHomeBanner(@Field("types")String types);

    //获取消息
    @POST("user/info/getNotice")
    @FormUrlEncoded
    Observable<MessageBean> getMessage(@Field("userId")String userId, @Field("types")String types);

    //系统公告
    @POST("index/notice")
    @FormUrlEncoded
    Observable<SystemMsgBean> getSysMsg(@Field("type")String types);

    //版本更新
    @POST("index/queryVersion")
    @FormUrlEncoded
    Observable<GetVersionBean> queryVersion(@Field("typen")String type);

    //删除收货地址
    @POST("address/delete")
    @FormUrlEncoded
    Observable<DeleteAddressBean> deleteAddress(@Field("id")String id);

    //添加收货地址
    @POST("address/add")
    @FormUrlEncoded
    Observable<DeleteAddressBean> addAddress(@Field("userId")String userId,@Field("name")String name,@Field("phone")String phone,@Field("province")String province,@Field("city")String city,@Field("district")String district,@Field("area")String area,@Field("status")String status);

    //获取收货地址列表
    @POST("address/getListByUserId")
    @FormUrlEncoded
    Observable<AddressListBean> getAddressList(@Field("userId")String userId);

    //修改收货地址
    @POST("address/update")
    @FormUrlEncoded
    Observable<DeleteAddressBean> update(@Field("userId")String userId,@Field("id")String id,@Field("name")String name,@Field("phone")String phone,@Field("province")String province,@Field("city")String city,@Field("district")String district,@Field("area")String area,@Field("status")String status);

}
