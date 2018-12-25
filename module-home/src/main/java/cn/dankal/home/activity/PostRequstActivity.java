package cn.dankal.home.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
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
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.HomeServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.callback.DKCallBackObject;
import cn.dankal.basiclib.bean.PostRequestBean;
import cn.dankal.basiclib.common.camera.CameraHandler;
import cn.dankal.basiclib.common.camera.RequestCodes;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.template.personal.ChangeAvatar;
import cn.dankal.basiclib.template.personal.ChangeAvatarImpl;
import cn.dankal.basiclib.util.FileUtil;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.AvatarUtil;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.util.image.ImagePathUtil;
import cn.dankal.basiclib.widget.TimeDialog;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.home.persenter.PostRequestContact;
import cn.dankal.home.persenter.PostRequestPresenter;
import cn.dankal.my.activity.PersonalData_EnActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.dankal.basiclib.protocol.HomeProtocol.POSTREQUEST;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

@Route(path = POSTREQUEST)
public class PostRequstActivity extends BaseActivity implements PostRequestContact.pcview, View.OnClickListener {
    private android.widget.Button submitBtn;
    private android.widget.ImageView colseImg;
    private android.widget.TextView titleThe;
    private android.widget.TextView titleSize;
    private android.widget.EditText titleEt;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView addImgRv;
    private android.widget.TextView periodText;
    private android.widget.EditText periodStart;
    private android.widget.EditText periodEnd;
    private android.widget.TextView priceText;
    private android.widget.LinearLayout priceMinLl;
    private android.widget.EditText priceMin;
    private android.widget.LinearLayout priceMaxLl;
    private android.widget.EditText priceMax;
    private android.widget.EditText contentEt;
    private int size = 5;
    private List<Uri> result = new ArrayList<>();
    private ImageRvAdapter imageRvAdapter;
    private static List<String> images = new ArrayList<>();
    final private static int KeyboardHeightLimit = 200;
    private RelativeLayout rlContent;
    private RelativeLayout rlOut;
    private PostRequestPresenter postRequestPresenter;
    private AlertDialog DIALOG;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String photoPath;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_request;
    }

    @Override
    protected void initComponents() {
        initView();
        SpannableString spannableString = new SpannableString("POST REQUEST");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.home_green));
        spannableString.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleThe.setText(spannableString);

        colseImg.setOnClickListener(v -> finish());
        periodEnd.setInputType(InputType.TYPE_NULL);
        periodStart.setInputType(InputType.TYPE_NULL);

        images = new ArrayList<>();
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SpannableString spannableString = new SpannableString(titleEt.getText().toString().trim().length() + "/50");
                ForegroundColorSpan colorSpan;
                if (titleEt.getText().toString().trim().length() >= 50) {
                    colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorFE3824));
                } else {
                    colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
                }
                spannableString.setSpan(colorSpan, 0, titleSize.getText().length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                titleSize.setText(spannableString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rlOut.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rlOut.getWindowVisibleDisplayFrame(r);
            final int screenHeight = rlOut.getRootView().getHeight();
            final int keyboardHeight = screenHeight - (r.bottom);

            if (keyboardHeight > KeyboardHeightLimit) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rlOut.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, keyboardHeight);
                rlOut.setLayoutParams(layoutParams);
            } else {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rlOut.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                rlOut.setLayoutParams(layoutParams);
            }
        });

        periodStart.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), "timeDialog");
                timeDialog.setListener(time -> periodStart.setText(time));
            }
        });
        periodEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.show(getSupportFragmentManager(), "timeDialog");
                timeDialog.setListener(time -> {
                    if (getTimeCompareSize(periodStart.getText().toString(), time) == 1) {
                        ToastUtils.showShort("The end time should not be earlier than the start time");
                    } else {
                        periodEnd.setText(time);
                    }
                });
            }
        });
        periodStart.setOnClickListener(v -> {
            TimeDialog timeDialog = new TimeDialog();
            timeDialog.show(getSupportFragmentManager(), "timeDialog");
            timeDialog.setListener(time -> periodStart.setText(time));
        });
        periodEnd.setOnClickListener(v -> {
            TimeDialog timeDialog = new TimeDialog();
            timeDialog.show(getSupportFragmentManager(), "timeDialog");
            timeDialog.setListener(time -> {
                if (getTimeCompareSize(periodStart.getText().toString(), time) == 1) {
                    ToastUtils.showShort("The end time should not be earlier than the start time");
                } else {
                    periodEnd.setText(time);
                }
            });
        });

        addImg.setOnClickListener(v -> beginCameraDialog());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        addImgRv.setLayoutManager(linearLayoutManager);

        submitBtn.setOnClickListener(v -> {
            post();
        });
    }


    private void post() {
        String title = titleEt.getText().toString().trim();
        String content = contentEt.getText().toString().trim();
        String start_price = priceMin.getText().toString().trim();
        String end_price = priceMax.getText().toString().trim();
        String start_date = periodStart.getText().toString().trim();
        String end_date = periodEnd.getText().toString().trim();
        if (title == null || title.length() == 0) {
            ToastUtils.showShort("Please fill in the title");
        } else if (title.length() < 4) {
            ToastUtils.showShort("The length of title cannot be less than 4");
        } else if (content.length() < 15) {
            ToastUtils.showShort("The length of description cannot be less than 15");
        } else {
            postRequestPresenter = new PostRequestPresenter();
            postRequestPresenter.attachView(this);
            PostRequestBean postRequestBean = new PostRequestBean();
            postRequestBean.setTitle(title);
            postRequestBean.setDescription(content);

            if (!start_date.equals("")) {
                if (end_date.equals("")) {
                    ToastUtils.showShort("Please fill in the end date");
                    return;
                }
            }

            if (!end_date.equals("")) {
                if (start_date.equals("")) {
                    ToastUtils.showShort("Please fill in the start date");
                    return;
                }
            }

            postRequestBean.setStart_date(start_date);
            postRequestBean.setEnd_date(end_date);

            if (!start_price.equals("")) {
                if (end_price.equals("")) {
                    ToastUtils.showShort("Please fill in the maximum price");
                    return;
                }

                if (Integer.valueOf(start_price) <= 0) {
                    ToastUtils.showShort("The minimum price cannot be $0");
                    return;
                }

                if (Integer.valueOf(start_price) >= Integer.valueOf(end_price)) {
                    ToastUtils.showShort("The starting price should be less than the maximum price");
                    return;
                }

            }

            if (!end_price.equals("")) {
                if (start_price.equals("")) {
                    ToastUtils.showShort("Please fill in the minimum price");
                    return;
                }
            }

            postRequestBean.setStart_price(start_price);
            postRequestBean.setEnd_price(end_price);
            postRequestBean.setImages(images);
            postRequestPresenter.post(postRequestBean);
        }
    }

    public static int getTimeCompareSize(String startTime, String endTime) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//年-月-日
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
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

            appCompatButton1.setText("PHOTOGRAPH");
            appCompatButton2.setText("SELECT FROM ALBUM");
            appCompatButton.setText("CANCEL");


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
                uploadQiniu(result.get(result.size()-1), this);

                imageRvAdapter = new ImageRvAdapter(this, result);
                addImgRv.setAdapter(imageRvAdapter);
                imageRvAdapter.setOnClickListener(pos -> {
                    result.remove(pos);
                    images.remove(pos);
                    imageRvAdapter.UpData(result);
                    addImg.setVisibility(View.VISIBLE);
                });
            }
            return;
        }

        if(requestCode==ResultCode.TakeImageCode){
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
            addImgRv.setAdapter(imageRvAdapter);
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
        submitBtn = findViewById(R.id.submit_btn);
        colseImg = findViewById(R.id.colse_img);
        titleThe = findViewById(R.id.title_the);
        titleSize = findViewById(R.id.title_size);
        titleEt = findViewById(R.id.title_et);
        addImg = findViewById(R.id.add_img);
        addImgRv = findViewById(R.id.add_img_Rv);
        periodText = findViewById(R.id.period_text);
        periodStart = findViewById(R.id.period_start);
        periodEnd = findViewById(R.id.period_end);
        priceText = findViewById(R.id.price_text);
        priceMinLl = findViewById(R.id.price_min_ll);
        priceMin = findViewById(R.id.price_min);
        priceMaxLl = findViewById(R.id.price_max_ll);
        priceMax = findViewById(R.id.price_max);
        contentEt = findViewById(R.id.content_et);
        rlContent = findViewById(R.id.rl_content);
        rlOut = findViewById(R.id.rl_out);
    }

    //图片上传至七牛
    public static void uploadQiniu(Uri uri, Context context) {
        final String[] path = {null};
        TipDialog loadingDialog;

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("uploading").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("Uploaded successfully").create(500);
                dialog.show();
                dialog.dismiss();
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
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? uri.getPath() : UriUtils.getPath(context, uri));
    }

    @Override
    public void postSuccess() {
        finish();
    }


    @Override
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.photodialog_btn_take){
            goCamera();
            DIALOG.cancel();
        }else if(i==R.id.photodialog_btn_native){
            CheckImage.takePhotoPicker(PostRequstActivity.this,1);
            DIALOG.cancel();
        }else if(i==R.id.photodialog_btn_cancel){
            DIALOG.cancel();
        }
    }

    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(PostRequstActivity.this, "cn.dankal.basiclib.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        PostRequstActivity.this.startActivityForResult(intent, ResultCode.TakeImageCode);
    }


    public void lubanPhoto(Uri uri) {
        Luban.with(PostRequstActivity.this).load(uri).ignoreBy(100).filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                uploadQiniu(Uri.parse(file.toString()), PostRequstActivity.this);
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch();
    }
}
