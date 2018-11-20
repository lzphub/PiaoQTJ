package cn.dankal.basiclib.template.personal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
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
    public void checkPermission(CameraHandler cameraHandler, DKCallBack agreePermission) {
        new RxPermissions(cameraHandler.getActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (!granted) {
                        ToastUtils.showShort("请开启相关权限，否则无法上传图片哦~");
                    } else {
                        if (agreePermission!=null){
                            agreePermission.action();
                        }else {
                            cameraHandler.beginCameraDialog();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, PersonalData_EnBean personalData_enBean) {
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                Uri takePath = CamerImageBean.getInstance().getPath();
                uploadPic(takePath ,personalData_enBean);
                break;
            case RequestCodes.PICK_PHOTO:
                Uri pickpath = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                uploadPic(pickpath ,personalData_enBean);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult2(int requestCode, int resultCode, Intent data, PersonalData_EngineerPostBean personalData_engineerPostBean) {
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                Uri takePath = CamerImageBean.getInstance().getPath();
                uploadPic2(takePath ,personalData_engineerPostBean);
                break;
            case RequestCodes.PICK_PHOTO:
                Uri pickpath = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                uploadPic2(pickpath ,personalData_engineerPostBean);
                break;
            default:
        }
    }

    @Override
    public void onChatPickPhoto(RecyclerView recyclerView, ServiceRvAdapter serviceRvAdapter, List<ServiceTextBean> serviceTextBeanList, int requestCode, int resultCode, Intent data) {
        ServiceTextBean serviceTextBean=new ServiceTextBean();
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
        recyclerView.scrollToPosition(serviceTextBeanList.size()-1);
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

    private void uploadPic2(Uri photoUris, PersonalData_EngineerPostBean personalData_engineerPostBean) {
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
                personalData_engineerPostBean.setAvatar(key);
                setAvatar2(personalData_engineerPostBean);
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
                PicUtils.loadAvatar(personalData_enBean.getAvatar(),mIvHead);
                ToastUtils.showShort("Uploaded successfully");
            }
        });
    }
    private void setAvatar2(PersonalData_EngineerPostBean personalData_engineerPostBean) {
        MyServiceFactory.engineerUpdateInfo(personalData_engineerPostBean).safeSubscribe(new AbstractDialogSubscriber<String>(view) {
            @Override
            public void onNext(String s) {
                PicUtils.loadAvatar(personalData_engineerPostBean.getAvatar(),mIvHead);
                ToastUtils.showShort("上传成功");
            }
        });
    }
}
