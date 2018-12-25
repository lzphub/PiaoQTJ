package cn.dankal.basiclib.template.personal;

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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.BuildConfig;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.adapter.ServiceRvAdapter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.base.callback.DKCallBack;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.bean.PersonalData_EngineerPostBean;
import cn.dankal.basiclib.common.camera.CamerImageBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.common.camera.RequestCodes;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ImagePathUtil;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.TipDialog;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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

    //目前target版本改成了23，如果要改成高版本需要根据下面的修改
    //https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
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
                Luban.with(context)
                        .load(data.getData())
                        .ignoreBy(100)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {
                                uploadPic(Uri.parse(file.getPath()), personalData_enBean);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }).launch();
            default:
        }
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


    @Override
    public void onActivityResult2(int requestCode, int resultCode, Intent data, PersonalData_EngineerPostBean personalData_engineerPostBean) {
        Uri uri;
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                uri = CamerImageBean.getInstance().getPath();
                startPhotoZoom(uri, 410, 410);
                break;
            case RequestCodes.PICK_PHOTO:

                File file = new File(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                Uri temUri = Uri.fromFile(file);
                startPhotoZoom(temUri, 410, 410);
                break;
            case RequestCodes.PICTURE_CUT:
//                if (data.getData() == null) {
//                    uri = CamerImageBean.getInstance().getPath();
//                } else {
//                    uri = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
//                }
                uploadPic2(data.getData(), personalData_engineerPostBean);
            default:
        }
    }

    private void luBanPhoto(Uri file,BaseView baseView){
        Luban.with(context)
                .load(file)
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        uploadPic3(Uri.parse(file.getPath()), baseView);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }


    
    @Override
    public void onChatPickPhoto(BaseView baseView, String pic, RecyclerView recyclerView, ServiceRvAdapter serviceRvAdapter, List<ChatBean.DataBean> serviceTextBeanList, int requestCode, int resultCode, Intent data) {
        ChatBean.DataBean serviceTextBean = new ChatBean.DataBean();
        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                Uri takePath = CamerImageBean.getInstance().getPath();

                serviceTextBean.setContent("/"+takePath.toString().substring(8));
                serviceTextBean.setType(2);
                //进行图片压缩
                luBanPhoto(takePath,baseView);

                serviceTextBeanList.add(serviceTextBean);
                serviceRvAdapter.update(serviceTextBeanList, takePath.toString());
                recyclerView.scrollToPosition(serviceTextBeanList.size() - 1);
                break;
            case RequestCodes.PICK_PHOTO:
                Uri pickpath = Uri.parse(ImagePathUtil.getImageAbsolutePath(context, data.getData()));
                serviceTextBean.setContent(pickpath.toString());
                serviceTextBean.setType(2);
                //进行图片压缩
                luBanPhoto(Uri.parse("file:///"+pickpath),baseView);

                serviceTextBeanList.add(serviceTextBean);
                serviceRvAdapter.update(serviceTextBeanList, pickpath.toString());
                recyclerView.scrollToPosition(serviceTextBeanList.size() - 1);
                break;
            default:
        }
    }



    //用户端头像
    private void uploadPic(Uri photoUris, PersonalData_EnBean personalData_enBean) {
        final File tempFile = new File(photoUris.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("UpLoading").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, photoUris) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                personalData_enBean.setAvatar(key);
                setAvatar(personalData_enBean);
                File deletefile=new File(localPath);
                context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{localPath});//删除系统缩略图
                deletefile.delete();
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? photoUris.getPath() : UriUtils.getPath(context, photoUris));

    }

    //工程师端头像
    private void uploadPic2(Uri photoUris, PersonalData_EngineerPostBean personalData_engineerPostBean) {
        final File tempFile = new File(photoUris.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("正在上传").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, photoUris) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();

                personalData_engineerPostBean.setAvatar(key);
                setAvatar2(personalData_engineerPostBean);
                File deletefile=new File(localPath);
                context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{localPath});//删除系统缩略图
                deletefile.delete();
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? photoUris.getPath() : UriUtils.getPath(context, photoUris));
    }

    //客服聊天图片上传
    private void uploadPic3(Uri photoUris, BaseView baseView) {
        final File tempFile = new File(photoUris.getPath());

        String type = SharedPreferencesUtils.getString(context, "identity", "user");
        TipDialog.Builder builder = new TipDialog.Builder(context);
        if (type.equals("user")) {
            loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("Send...").create();
        } else {

            loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("正在发送").create();
        }
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, photoUris) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();

                if (type.equals("user")) {
                    MyServiceFactory.userServiceSendMsg(key, 2).safeSubscribe(new AbstractDialogSubscriber<String>(baseView) {
                        @Override
                        public void onNext(String s) {
                            TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("Send a success").create(500);
                            dialog.show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    MyServiceFactory.serviceSendMsg(key, 2).safeSubscribe(new AbstractDialogSubscriber<String>(baseView) {
                        @Override
                        public void onNext(String s) {
                            TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("发送成功").create(500);
                            dialog.show();
                            dialog.dismiss();
                        }
                    });
                }
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? photoUris.getPath() : UriUtils.getPath(context, photoUris));

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

    private void setAvatar2(PersonalData_EngineerPostBean personalData_engineerPostBean) {
        MyServiceFactory.engineerUpdateInfo(personalData_engineerPostBean).safeSubscribe(new AbstractDialogSubscriber<String>(view) {
            @Override
            public void onNext(String s) {
                PicUtils.loadAvatar(personalData_engineerPostBean.getAvatar(), mIvHead);
                ToastUtils.showShort("上传成功");
            }
        });
    }
}
