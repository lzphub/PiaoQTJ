package cn.dankal.basiclib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;

import cn.dankal.basiclib.common.cache.SDCacheDir;
import cn.dankal.basiclib.common.cache.SDCacheDirCompat;
import cn.dankal.basiclib.util.AppUtils;
import cn.dankal.basiclib.util.DensityAdaptationUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.widget.loadsir.EmptyCallback;
import cn.dankal.basiclib.widget.loadsir.EmptyEnCallback;
import cn.dankal.basiclib.widget.loadsir.LoadingCallback;
import cn.dankal.basiclib.widget.loadsir.RetryCallback;
import cn.dankal.basiclib.widget.loadsir.core.LoadSir;


/**
 * @author Dankal Android Developer
 * @since 2018/5/9
 */

public class DankalApplication extends Application {
    //是否是开发环境
    public static final boolean isDev = true;

    private static DankalApplication context;

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
        DankalApplication.login = login;
    }

    public static boolean isLogin() {
        return login || (DKUserManager.getUserInfo() != null
                && StringUtil.isValid(DKUserManager.getUserInfo().getUuid()));
    }
}
