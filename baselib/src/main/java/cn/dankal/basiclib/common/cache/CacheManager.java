package cn.dankal.basiclib.common.cache;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.io.File;
import java.text.DecimalFormat;


/**
 * Created by Dylan on 2016/9/27.
 */
public class CacheManager {
    private static long totalCacheSize;
    private static String[] undeleteSharePreferences;
    private static CacheManager cacheManager;

    private CacheManager() {

    }

    public static CacheManager getInstance() {
        synchronized (cacheManager) {
            if (cacheManager == null) {
                cacheManager = new CacheManager();
            } else
                return cacheManager;

        }
        return cacheManager;
    }


    /**
     * @param context
     * @param parentPaths 自定义的路径
     * @param obeserver
     */
    public static void cleanCache(Context context, CacheSizeObeserver obeserver, String... parentPaths) {
        long size_long = cleanCache(context, parentPaths);
        String size_str = Formatter.formatFileSize(context, size_long);
        obeserver.cacheSize(size_long, size_str);
    }


    public static long cleanCache(Context context, String... parentPaths) {
        //清除SDcard
        String cachepath = SDCacheDir.getInstance(context).cachepath;
        String filepath = SDCacheDir.getInstance(context).filesDir;
        deleteFolderFile(cachepath);
        deleteFolderFile(filepath);
        if (parentPaths != null && parentPaths.length > 0) {
            for (String path : parentPaths) {
                if (path != null && !path.isEmpty()) {
                    deleteFolderFile(path);
                }
            }
        }


        return getTotalSize(context, parentPaths);
    }

    /**
     * @param context
     * @param parentPaths 自定义路径的父文件路径即文件夹的路径
     * @return
     */
    public static long getTotalSize(Context context, String... parentPaths) {
        totalCacheSize = 0;
        //获取SDcard下的缓存
        try {
            totalCacheSize += getFolderSize(new File(SDCacheDir.getInstance(context).cachepath));
            totalCacheSize += getFolderSize(new File(SDCacheDir.getInstance(context).filesDir));

            if (parentPaths != null && parentPaths.length > 0) {
                for (String path : parentPaths) {
                    if (path != null && !path.isEmpty())
                        totalCacheSize += getFolderSize(new File(path));//加上自定的路径
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCacheSize;
    }


    /**
     * 获取sdcard中的缓存
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 获取sdcard中的缓存
     */
    public static String getFolderSizeTxt(File file) throws Exception {
        long folderSize = getFolderSize(file);
        DecimalFormat df = new DecimalFormat("#0.00");
        if (folderSize < 1024) {
            return folderSize + "B";
        } else if (folderSize < 1024 * 1024) {
            return df.format(folderSize / 1024F) + "KB";
        } else /*if (folderSize < 1024 * 3)*/ {
            return df.format(folderSize / (1024F * 1024)) + "MB";
        }
    }


    public static void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File[] files = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getPath());
                    }
                } else {
                    if (file != null && file.length() > 0) {
                        file.delete();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
