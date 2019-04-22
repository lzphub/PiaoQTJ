package cn.xunzi.basiclib.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.xunzi.basiclib.domain.HttpLoggingInterceptor;
import cn.xunzi.basiclib.domain.TokenInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

import static cn.xunzi.basiclib.XunziApplication.isDev;

public class BaseApi {
    public static final String BASE_URL = "http://47.92.236.11:8999/";
    public static final String IMAGE_URL="http://47.92.236.11:8999";


//    public static final String BASE_URL = "http://192.168.101.15:8999/";
//    public static final String IMAGE_URL="http://192.168.101.15:8999";


    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkhttpInstance())
                .baseUrl(BASE_URL)
                .build();
    }

    public static Retrofit getRetrofit(String baseurl) {
        return new Retrofit. Builder()
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(getOkhttpInstance())
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static OkHttpClient getOkhttpInstance() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient
                okHttpClient = new OkHttpClient.Builder().readTimeout(7000, TimeUnit.MILLISECONDS)
                .connectTimeout(7000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    /**
                     * Verify that the host name is an acceptable match with
                     * the server's authentication scheme.
                     *
                     * @param hostname the host name
                     * @param session  SSLSession used on the connection to host
                     * @return true if the host name is acceptable
                     */
                    //忽略证书
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        return okHttpClient;
    }

}
