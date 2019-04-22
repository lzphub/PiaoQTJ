package cn.xunzi.basiclib;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;

import cn.xunzi.basiclib.common.cache.SDCacheDir;
import cn.xunzi.basiclib.common.cache.SDCacheDirCompat;
import cn.xunzi.basiclib.util.AppUtils;
import cn.xunzi.basiclib.util.DensityAdaptationUtils;
import cn.xunzi.basiclib.util.StringUtil;
import cn.xunzi.basiclib.widget.loadsir.EmptyCallback;
import cn.xunzi.basiclib.widget.loadsir.EmptyEnCallback;
import cn.xunzi.basiclib.widget.loadsir.LoadingCallback;
import cn.xunzi.basiclib.widget.loadsir.RetryCallback;
import cn.xunzi.basiclib.widget.loadsir.core.LoadSir;
//import cn.jpush.android.api.JPushInterface;


/**
 * @author xunzi Android Developer
 * @since 2018/5/9
 */

public class XunziApplication extends Application {
    //是否是开发环境
    public static final boolean isDev = true;

    private static XunziApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initARouter();
        initStetho();
        initLoadSir();
        initSDCache();
        //适配方案
        DensityAdaptationUtils.setDensity(context, 375);
        AppUtils.init(context);

        //初始化极光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }
    /**
     * 初始化阿里路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new RetryCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyEnCallback())
//                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    private void initSDCache(){
        SDCacheDirCompat.getInstance().setRootCacheFile(
                new SDCacheDir(getContext()).filesDir);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * facebook出品辅助开发工具
     */
    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static boolean login;

    public static void setLogin(boolean login) {
        XunziApplication.login = login;
    }

    public static boolean isLogin() {
        return login || (XZUserManager.getUserInfo() != null
                && StringUtil.isValid(XZUserManager.getUserInfo().getId()+""));
    }
}
