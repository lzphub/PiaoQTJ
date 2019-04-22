package cn.xunzi.basiclib;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

import cn.xunzi.basiclib.api.BaseApi;


/**
 * @author vane
 * @since 2018/7/20
 */
@GlideModule
public class XzAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {

        /* 定义SD卡缓存大小和位置 */
        int diskSize = 1024 * 1024 * 100;
        // ExternalCacheDiskCacheFactory
        // /sdcard/Android/data/<application package>/cache
        glideBuilder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "glide", diskSize));

        /* 定义图片格式 */
        glideBuilder.setDefaultRequestOptions(new RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565));
    }

    /**
     * 注册源 Model 对应的 ModelLoader
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // 注册全局唯一OkHttp客户端(HttpClient初始化的情况)
        OkHttpUrlLoader.Factory factory_glideurl;
        try {
            factory_glideurl = new OkHttpUrlLoader.Factory(BaseApi.getOkhttpInstance());
        } catch (Exception e) {
            factory_glideurl = new OkHttpUrlLoader.Factory();
        }
        registry.replace(GlideUrl.class, InputStream.class, factory_glideurl);

//        // 注册视频获取缩略图的扩展
//        VideoModelLoader.Factory factory_videourl = new VideoModelLoader.Factory();
//        registry.replace(VideoUrl.class, InputStream.class, factory_videourl);
    }

    /**
     * 清单解析是否开启 <br>
     * 要注意避免重复添加 <br>
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
