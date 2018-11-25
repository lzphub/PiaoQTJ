package cn.dankal.basiclib.api;


import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.base.BaseResult;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.bean.ProductListBean;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

@ApiFactory(value = BaseApi.class)
public interface ProductService {

    /**
     * 商品列表和搜索
     */
    @GET("customer/product")
    Observable<ProductListBean> getProductlist(@Query("keyword")String keyword,@Query("category_uuid") String category_uuid);

    /**
     * 需求搜索
     */
    @GET("engineer/home/search")
    Observable<DemandListbean> searchDemandList(@Query("keyword")String keyword);

    /**
     * 商品详情
     */
    @GET("customer/product/{product_uuid}")
    Observable<ProductDataBean> getProductData(@Path("product_uuid")String product_uuid);

    /**
     * 添加收藏
     *
     */
    @POST("customer/product/addFavourite")
    @FormUrlEncoded
    Observable<String> addcollection(@Field("product_uuid")String product_uuid);

    /**
     * 取消收藏
     */
    @DELETE("customer/me/deletefavourite/{product_uuid}")
    Observable<String> deleteColl(@Path("product_uuid") String product_uuid);

    /**
     * 提交意向
     */
    @POST("customer/product/purchase/{product_uuid}")
    @FormUrlEncoded
    Observable<String> postPurchase(@Path("product_uuid")String product_uuid,@Field("contact_name")String contact_name,@Field("contact_phone")String contact_phone,@Field("email")String email,@Field("extra_info")String extra_info);
}
