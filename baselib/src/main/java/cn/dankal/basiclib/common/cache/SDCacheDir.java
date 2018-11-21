package cn.dankal.basiclib.common.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Dylan on 2016/10/12.
 */
public class SDCacheDir {

    private static SDCacheDir mInstance;
    private String sdpath;
    public String cachepath;
    public String filesDir;
    public String cachepath2;
    public String filesDir2;

    public SDCacheDir(Context context) {
        sdpath = Environment.getExternalStorageDirectory().toString();
        //手机外置缓存路径
        cachepath = sdpath + "/" + "Android/data/" + context.getPackageName() + "/cache/";
        filesDir = sdpath + "/" + "Android/data/" + context.getPackageName() + "/files/";
        //手机SD缓存路径
        cachepath2=context.getCacheDir().getPath()+ File.separator;
        filesDir2=context.getFilesDir().getPath()+ File.separator;
    }



    public static SDCacheDir getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SDCacheDir.class) {
                if (mInstance == null) {
                    mInstance = new SDCacheDir(context);
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

}
