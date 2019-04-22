package cn.xunzi.basiclib.template.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.DecimalFormat;

import api.MyServiceFactory;
import cn.xunzi.basiclib.BuildConfig;
import cn.xunzi.basiclib.base.BaseView;
import cn.xunzi.basiclib.base.callback.DKCallBack;
import cn.xunzi.basiclib.common.camera.CamerImageBean;
import cn.xunzi.basiclib.common.camera.CameraHandler;
import cn.xunzi.basiclib.common.camera.RequestCodes;
import cn.xunzi.basiclib.common.qiniu.QiniuUpload;
import cn.xunzi.basiclib.common.qiniu.UploadHelper;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ImagePathUtil;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.util.UriUtils;
import cn.xunzi.basiclib.util.image.PicUtils;
import cn.xunzi.basiclib.widget.TipDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.xunzi.basiclib.common.camera.RequestCodes.PICTURE_CUT;
import static cn.xunzi.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

/**
 * Date: 2018/8/2.
 * Time: 11:06
 * classDescription:
 *
 * @author fred
 */
public class ChangeAvatarImpl implements ChangeAvatar {

    private String tempFilePath;
    private Uri tempUri;
    private Context context;
    private TipDialog loadingDialog;
    private ImageView mIvHead;
    private BaseView view;

    public ChangeAvatarImpl(Context context, BaseView view) {
        this.context = context;
        this.view = view;

        String rootPath = Environment.getExternalStorageDirectory().toString() + "/" + "Android/data/" + context.getPackageName() + "/files/" + "temp";
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        tempFilePath = rootPath + "/temp.jpg";

    }

    @Override
    public void checkPermission(CameraHandler cameraHandler, DKCallBack agreePermission) {
        new RxPermissions(cameraHandler.getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(granted -> {
            if (!granted) {
                ToastUtils.showShort("请开启相关权限，否则无法上传图片哦~");
            } else {
                if (agreePermission != null) {
                    agreePermission.action();
                } else {
                    cameraHandler.beginCameraDialog();
                }
            }
        });
    }

    @Override
    public void setIvHead(ImageView imageView) {

    }

    //裁剪图片
    private void startPhotoZoom(Uri uri, int width, int height) {

        // TODO: 2018-11-19 context
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//重要的，，，
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            File file = new File(uri.getPath());
            uri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        }else{
            File file = new File(uri.getPath());
            uri=Uri.fromFile(file);
        }
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);// 输出图片大小
        intent.putExtra("outputY", height);
        //裁剪时是否保留图片的比例，这里的比例是1:1
        intent.putExtra("scale", true);
        //是否是圆形裁剪区域，设置了也不一定有效
        intent.putExtra("circleCrop", true);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, PICTURE_CUT);

    }


}
