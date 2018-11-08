package cn.dankal.basiclib.template.personal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.DecimalFormat;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.common.camera.CamerImageBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.common.camera.RequestCodes;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ImagePathUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.TipDialog;

import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

/**
 * Date: 2018/8/2.
 * Time: 11:06
 * classDescription:
 *
 * @author fred
 */
public class ChangeAvatarImpl implements ChangeAvatar {

    private Context context;
    private TipDialog loadingDialog;
    private ImageView mIvHead;
    private BaseView view;

    public ChangeAvatarImpl(Context context, BaseView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void checkPermission(CameraHandler cameraHandler) {
        new RxPermissions(cameraHandler.getActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (!granted) {
                        ToastUtils.showShort("请开启相关权限，否则无法上传图片哦~");
                    } else {
                        cameraHandler.beginCameraDialog();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                Uri takePath = CamerImageBean.getInstance().getPath();
                uploadPic(takePath);
                break;
            case RequestCodes.PICK_PHOTO:
                Uri pickpath = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                uploadPic(pickpath);
                break;
            default:
        }
    }

    private void uploadPic(Uri photoUris) {
        final File tempFile = new File(photoUris.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在上传").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, photoUris) == null;

//        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
//            @Override
//            public void onSucess(String localPath, String key) {
//                loadingDialog.dismiss();
//                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
//                        .setTipWord("上传成功,请等待审核")
//                        .create(2000);
//                dialog.show();
//                dialog.dismiss();
//
////                String path = PicUtil.getUrl(key);
////                Uri uri = Uri.fromFile(tempFile);
////                getIvHead().setImageURI(uri);
//                setAvatar(key);
//            }
//
//            @Override
//            public void onUpload(double percent) {
//                DecimalFormat df = new DecimalFormat("#0.00");
//                builder.setTipWord(df.format(percent * 100) + "%").showProgress();
//            }
//
//            @Override
//            public void onError(String string) {
//                ToastUtils.showLong(string);
//                loadingDialog.dismiss();
//                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL)
//                        .setTipWord("上传失败")
//                        .create(2000);
//                dialog.show();
//                dialog.dismiss();
//            }
//        }, b ? photoUris.getPath() : UriUtils.getPath(context, photoUris));

        Uri uri = Uri.fromFile(tempFile);
        mIvHead.setImageURI(uri);
    }


    @Override
    public void setIvHead(@NonNull ImageView mIvHead) {
        this.mIvHead = mIvHead;
    }

    private void setAvatar(String path) {
        UserServiceFactory.updateAvatar(path)
                .subscribe(new AbstractDialogSubscriber<String>(view) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showShort("上传成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
