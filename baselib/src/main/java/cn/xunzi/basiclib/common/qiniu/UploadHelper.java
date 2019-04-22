package cn.xunzi.basiclib.common.qiniu;


import cn.xunzi.basiclib.util.StringUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by Fred on 2016/10/19.
 */

public class UploadHelper {
    private QiniuUpload qiniuUpload;

    public UploadHelper() {
        qiniuUpload = new QiniuUpload();
    }


    public void uploadQiniuPic(QiniuUpload.UploadListener uploadListener, String localPath) {
        if (!StringUtil.isValid(localPath)) {
            uploadListener.onError("图片地址为空");
            return;
        }
        QiniuApi.getInstance().getQN()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiniuBean -> {
                    try {
                        String token = qiniuBean.getToken();
                        if (StringUtil.isValid(token)) {
                            qiniuUpload.beginQiniuImageUpload(token, localPath, uploadListener);
                        } else {
                            uploadListener.onError("图片上传失败");
                        }
                    } catch (Exception e) {
                        uploadListener.onError("图片上传失败");
                    }
                });
    }

    public void uploadQiniuVideo(QiniuUpload.UploadListener uploadListener, String localPath) {
        if (!StringUtil.isValid(localPath)) {
            uploadListener.onError("视频地址为空");
            return;
        }

        QiniuApi.getInstance().getQN()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiniuBean -> {
                    try {
                        String token = qiniuBean.getToken();
                        if (StringUtil.isValid(token)) {
                            qiniuUpload.beginQiniuVideoUpload(token, localPath, uploadListener);
                        } else {
                            uploadListener.onError("视频上传失败");
                        }
                    } catch (Exception e) {
                        uploadListener.onError("视频上传失败");
                    }
                });

    }

    public void cancle() {
        qiniuUpload.cancle();
    }
}
