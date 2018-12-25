package cn.dankal.my.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.setting.R;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.dankal.basiclib.protocol.MyProtocol.OPINION;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

/**
 * 意见反馈
 */
@Route(path = OPINION)
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.TextView title;
    private android.widget.ImageView addImg;
    private android.widget.Button submitBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.EditText etOpinion;
    private android.widget.TextView opinionSize;
    private android.support.v7.widget.RecyclerView imgList;
    private ImageRvAdapter imageRvAdapter;
    private int size = 3;
    private List<Uri> result = new ArrayList<>();
    private static String identity = null;
    private static List<String> images = new ArrayList<>();

    private AlertDialog DIALOG;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String photoPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initComponents() {
        initView();
        images = new ArrayList<>();
        identity = SharedPreferencesUtils.getString(this, "identity", "enterprise");
        if (identity.equals("user")) {
            titleText.setText("HELP AND FEEDBACK");
            title.setText("ADD IMAGE");
            etOpinion.setHint("* WE KNOW HOW TO LISTEN. PLEASE WRITE DOWN YOUR SUGGESTIONS HERE");
            submitBtn.setText("SAVE");
        }
        backImg.setOnClickListener(v -> finish());
        etOpinion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SpannableString spannableString = new SpannableString(etOpinion.getText().toString().trim().length() + "/200");
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
                spannableString.setSpan(colorSpan, 0, opinionSize.getText().length() - 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                opinionSize.setText(spannableString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addImg.setOnClickListener(v -> beginCameraDialog());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);

        submitBtn.setOnClickListener(v -> {
            if (identity.equals("user")) {
                MyServiceFactory.postFeedBack(etOpinion.getText().toString().trim(), images).safeSubscribe(new AbstractDialogSubscriber<String>(FeedBackActivity.this) {
                    @Override
                    public void onNext(String s) {
                        ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).withString("feedback", "feedback").navigation();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        if (e instanceof LocalException) {
                            LocalException exception = (LocalException) e;
                            if (exception.getMsg().equals("网络错误")) {
                                ToastUtils.showShort("Network error");
                            }
                        }
                    }
                });
            } else {
                MyServiceFactory.engPostFeedBack(etOpinion.getText().toString().trim(), images).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
                    @Override
                    public void onNext(String s) {
                        ARouter.getInstance().build(HomeProtocol.SUBMITINTENTION).withString("feedback", "feedback").navigation();
                        finish();
                    }
                });
            }
        });
    }

    public void beginCameraDialog() {
        DIALOG = new AlertDialog.Builder(this).create();
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(cn.dankal.basiclib.R.layout.dialog_photo_picker);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            //弹出一个窗口，让背后的窗口变暗一点
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //dialog背景层
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            AppCompatButton appCompatButton = window.findViewById(cn.dankal.basiclib.R.id.photodialog_btn_cancel);
            AppCompatButton appCompatButton1 = window.findViewById(cn.dankal.basiclib.R.id.photodialog_btn_take);
            AppCompatButton appCompatButton2 = window.findViewById(cn.dankal.basiclib.R.id.photodialog_btn_native);

            if ("user".equals(identity)) {
                appCompatButton1.setText("PHOTOGRAPH");
                appCompatButton2.setText("SELECT FROM ALBUM");
                appCompatButton.setText("CANCEL");

            }

            appCompatButton.setOnClickListener(this);
            appCompatButton1.setOnClickListener(this);
            appCompatButton2.setOnClickListener(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultCode.CheckImageCode) {
            if (data != null) {
                result.add(Matisse.obtainResult(data).get(0));
                if (result.size() == size) {
                    addImg.setVisibility(View.INVISIBLE);
                }
                uploadQiniu(result.get(result.size() - 1), this);
                imageRvAdapter = new ImageRvAdapter(this, result);
                imgList.setAdapter(imageRvAdapter);
                imageRvAdapter.setOnClickListener(pos -> {
                    result.remove(pos);
                    images.remove(pos);
                    imageRvAdapter.UpData(result);
                    addImg.setVisibility(View.VISIBLE);
                });
            }
            return;
        }

        if (requestCode == ResultCode.TakeImageCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }

            result.add(Uri.parse("file:///" + photoPath));
            if (result.size() == size) {
                addImg.setVisibility(View.INVISIBLE);
            }

            lubanPhoto(result.get(result.size() - 1));
            imageRvAdapter = new ImageRvAdapter(this, result);
            imgList.setAdapter(imageRvAdapter);
            imageRvAdapter.setOnClickListener(pos -> {
                result.remove(pos);
                images.remove(pos);
                imageRvAdapter.UpData(result);
                addImg.setVisibility(View.VISIBLE);
            });
        }
        return;
    }

    private void initView() {
        title = findViewById(R.id.title);
        addImg = findViewById(R.id.add_img);
        backImg = findViewById(R.id.back_img);
        imgList = findViewById(R.id.img_list);
        titleText = findViewById(R.id.title_text);
        etOpinion = findViewById(R.id.et_opinion);
        submitBtn = findViewById(R.id.submit_btn);
        opinionSize = findViewById(R.id.opinion_size);
    }

    //图片上传至七牛
    public static void uploadQiniu(Uri uri, Context context) {
        final String[] path = {null};
        TipDialog loadingDialog;

        TipDialog.Builder builder = new TipDialog.Builder(context);
        if (identity.equals("user")) {
            loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("uploading").create();
        } else {
            loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("正在上传").create();
        }
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                if (identity.equals("user")) {
                    TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("Uploaded successfully").create(2000);
                    dialog.show();
                    dialog.dismiss();
                } else {
                    TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("图片添加成功").create(500);
                    dialog.show();
                    dialog.dismiss();
                }

                images.add(key);
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(1000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? uri.getPath() : UriUtils.getPath(context, uri));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.photodialog_btn_take) {
            goCamera();
            DIALOG.cancel();
        } else if (i == R.id.photodialog_btn_native) {
            CheckImage.takePhotoPicker(FeedBackActivity.this, 1);
            DIALOG.cancel();
        } else if (i == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        }
    }

    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(FeedBackActivity.this, "cn.dankal.basiclib.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        FeedBackActivity.this.startActivityForResult(intent, ResultCode.TakeImageCode);
    }


    public void lubanPhoto(Uri uri) {
        Luban.with(FeedBackActivity.this).load(uri).ignoreBy(100).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                uploadQiniu(Uri.parse(file.toString()), FeedBackActivity.this);
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch();
    }
}
