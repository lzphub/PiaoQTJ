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

    public SDCacheDir(Context context) {
        sdpath = Environment.getExternalStorageDirectory().toString();
//        cachepath = sdpath + "/" + "Android/data/" + DKApp.getContext().getPackageName() + "/cache/";
//        filesDir = sdpath + "/" + "Android/data/" + DKApp.getContext().getPackageName() + "/files/";
        cachepath=context.getCacheDir().getPath()+ File.separator;
        filesDir=context.getFilesDir().getPath()+ File.separator;
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
