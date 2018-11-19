package cn.dankal.basiclib.template.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.List;

import api.MyServiceFactory;
import api.UserServiceFactory;
import cn.dankal.basiclib.adapter.ServiceRvAdapter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.base.callback.DKCallBack;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
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

import static cn.dankal.basiclib.common.camera.RequestCodes.PICTURE_CUT;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

/**
 * Date: 2018/8/2.
 * Time: 11:06
 * classDescription:
 *
 * @author fred
 */
public class ChangeAvatarImpl implements ChangeAvatar {

    private String tempFilePath;
    private Context context;
    private TipDialog loadingDialog;
    private ImageView mIvHead;
    private BaseView view;

    public ChangeAvatarImpl(Context context, BaseView view) {
        this.context = context;
        this.view = view;

        String rootPath = Environment.getExternalStorageDirectory().toString() +
                "/" + "Android/data/" + context.getPackageName() + "/files/"
                + "temp";
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        tempFilePath = rootPath + "/temp.jpg";

    }

    @Override
    public void checkPermission(CameraHandler cameraHandler, DKCallBack agreePermission) {
        new RxPermissions(cameraHandler.getActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data, PersonalData_EnBean personalData_enBean) {
        Uri uri;
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                uri = CamerImageBean.getInstance().getPath();
                startPhotoZoom(uri, 400, 400);
                break;
            case RequestCodes.PICK_PHOTO:

                File file = new File(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                Uri temUri = Uri.fromFile(file);
                startPhotoZoom(temUri, 400, 400);
                break;
            case RequestCodes.PICTURE_CUT:
//                if (data.getData() == null) {
//                    uri = CamerImageBean.getInstance().getPath();
//                } else {
//                    uri = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
//                }
                uploadPic(Uri.parse(tempFilePath), personalData_enBean);
            default:
        }
    }


    //裁剪图片
    private void startPhotoZoom(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        File file = new File(tempFilePath);
        Uri temUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, temUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, PICTURE_CUT);
        }
        // TODO: 2018-11-19 context
    }

    @Override
    public void onChatPickPhoto(RecyclerView recyclerView, ServiceRvAdapter serviceRvAdapter, List<ServiceTextBean> serviceTextBeanList, int requestCode, int resultCode, Intent data) {
        ServiceTextBean serviceTextBean = new ServiceTextBean();
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                Uri takePath = CamerImageBean.getInstance().getPath();
                serviceTextBean.setSend_img(takePath);
                serviceTextBean.setType(3);
                break;
            case RequestCodes.PICK_PHOTO:
                Uri pickpath = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                serviceTextBean.setSend_img(pickpath);
                serviceTextBean.setType(3);
                break;
            default:
        }
        serviceTextBeanList.add(serviceTextBean);
        serviceRvAdapter.update(serviceTextBeanList);
        recyclerView.scrollToPosition(serviceTextBeanList.size() - 1);
    }


    private void uploadPic(Uri photoUris, PersonalData_EnBean personalData_enBean) {
        final File tempFile = new File(photoUris.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在上传").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, photoUris) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("上传成功,请等待审核")
                        .create(2000);
                dialog.show();
                dialog.dismiss();

//                String path = PicUtil.getUrl(key);
//                Uri uri = Uri.fromFile(tempFile);
//                getIvHead().setImageURI(uri);
                personalData_enBean.setAvatar(key);
                setAvatar(personalData_enBean);
            }

            @Override
            public void onUpload(double percent) {
                DecimalFormat df = new DecimalFormat("#0.00");
                builder.setTipWord(df.format(percent * 100) + "%").showProgress();
            }

            @Override
            public void onError(String string) {
                ToastUtils.showLong(string);
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL)
                        .setTipWord("上传失败")
                        .create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? photoUris.getPath() : UriUtils.getPath(context, photoUris));

//        Uri uri = Uri.fromFile(tempFile);
//        mIvHead.setImageURI(uri);
    }


    @Override
    public void setIvHead(@NonNull ImageView mIvHead) {
        this.mIvHead = mIvHead;
    }

    private void setAvatar(PersonalData_EnBean personalData_enBean) {
        MyServiceFactory.updateInfo(personalData_enBean).safeSubscribe(new AbstractDialogSubscriber<String>(view) {
            @Override
            public void onNext(String s) {
                PicUtils.loadAvatar(personalData_enBean.getAvatar(), mIvHead);
                ToastUtils.showShort("Uploaded successfully");
            }
        });
    }
}
