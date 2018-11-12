package cn.dankal.basiclib.api;


import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.base.BaseResult;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.bean.ProductListBean;
import retrofit2.http.Body;
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
     * 分类商品列表
     *
     */
    @GET("customer/product")
    Observable<ProductListBean> getProductlist(@Query("category_uuid") String category_uuid);

    /**
     * 商品详情
     *
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
}
