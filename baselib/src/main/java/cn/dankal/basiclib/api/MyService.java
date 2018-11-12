package cn.dankal.basiclib.api;



import cn.dankal.annotations.ApiFactory;
import cn.dankal.basiclib.bean.AboutUsBean;
import io.reactivex.Observable;
import retrofit2.http.POST;

@ApiFactory(value = BaseApi.class)
public interface MyService {

    @POST("customer/me/getaboutus")
    Observable<AboutUsBean> getAboutUs();
}
