package cn.dankal.basiclib.common.qiniu;


import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import cn.dankal.basiclib.DKUserManager;


/**
 * Created by Fred on 2016/10/19.
 */

public class QiniuUpload {
    private boolean isCancelled = false;
    private UploadManager normaluploadManager;

    public QiniuUpload() {
        normalinit();
    }

    //普通上传初始化
    private void normalinit() {
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        if (normaluploadManager == null) {
            normaluploadManager = new UploadManager();
        }
    }

    //开始图片上传
    protected void beginQiniuImageUpload(String token, final String path, final UploadListener uploadListener) {
      /*  data = <File对象、或 文件路径、或 字节数组>
        String key = <指定七牛服务上的文件名，或 null>;
        String token = <从服务端SDK获取>;*/


         String user_id = String.valueOf(DKUserManager.getUserInfo().getUuid());
        String key = "zm_"+ String.valueOf(System.currentTimeMillis())+ user_id+".png";
        normaluploadManager.put(path, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isNetworkBroken() || info.isServerError()) {
//                    Logger.e(info.error);
                    uploadListener.onError(info.error);
                } else {
                    if (info.isOK()) {
                        //key是不完全的图片地址
                        uploadListener.onSucess(path, key);
                    }else{
                        uploadListener.onError(info.error);
                    }
                }
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                uploadListener.onUpload(percent);
            }
        }, () -> isCancelled));
    }

    //开始图片上传
    protected void beginQiniuVideoUpload(String token, final String localPath, final UploadListener uploadListener) {
      /*  data = <File对象、或 文件路径、或 字节数组>
        String key = <指定七牛服务上的文件名，或 null>;
        String token = <从服务端SDK获取>;*/


        String user_id = String.valueOf(DKUserManager.getUserInfo().getUuid());
        String key = "cheersto_" + String.valueOf(System.currentTimeMillis())+ user_id+".mp4";
        normaluploadManager.put(localPath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isNetworkBroken() || info.isServerError()) {
//                    Logger.e(info.error);
                    uploadListener.onError(info.error);
                } else {
                    if (info.isOK()) {
                        //key是不完全的图片地址
                        uploadListener.onSucess(localPath, key);
                    }else{
                        uploadListener.onError(info.error);
                    }
                }
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                uploadListener.onUpload(percent);
            }
        }, () -> isCancelled));
    }


    public interface UploadListener {
        void onSucess(String filepath, String key);


        void onUpload(double percent);

        void onError(String string);
    }


    //取消上传

    // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
    protected void cancle() {
        isCancelled = true;
    }
}
