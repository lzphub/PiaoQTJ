package cn.dankal.home.activity;

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
import android.text.TextWatcher;
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

import api.HomeServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProjectDataBean;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.CheckImage;
import cn.dankal.basiclib.adapter.ImageRvAdapter;
import cn.dankal.basiclib.widget.TipDialog;

import static cn.dankal.basiclib.protocol.HomeProtocol.CLAIMDEMAND;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

@Route(path = CLAIMDEMAND)
public class DemandClaimActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.Button submitBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView demandPrice;
    private android.widget.TextView releaseTime;
    private android.widget.TextView demandTitle;
    private android.widget.TextView demandData;
    private TextView demand_content;
    private android.widget.ImageView addImg;
    private android.support.v7.widget.RecyclerView imgList;
    private android.widget.TextView sizeDetails;
    private android.widget.EditText detailsEt;
    private List<Uri> result = new ArrayList<>();
    private int size = 5;
    private ImageRvAdapter imageRvAdapter;
    private static List<String> images = new ArrayList<>();
    private ProjectDataBean dataBean;
    private String time;
    private String plan_uuid="";

    private AlertDialog DIALOG;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String photoPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_claim;
    }

    @Override
    public void initComponents() {
        initView();
        dataBean = (ProjectDataBean) getIntent().getSerializableExtra("demandData");
        time = getIntent().getStringExtra("time");
        plan_uuid = getIntent().getStringExtra("plan_uuid");
        demandTitle.setText(dataBean.getName());
        demand_content.setText(dataBean.getDesc());
        demandPrice.setText("¥" + StringUtil.isDigits(dataBean.getStart_price()) + " ~ " + StringUtil.isDigits(dataBean.getEnd_price()));
        releaseTime.setText(time);
        backImg.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plan_uuid==null) {
                    HomeServiceFactory.postplan(dataBean.getUuid(), detailsEt.getText().toString().trim(), images).safeSubscribe(new AbstractDialogSubscriber<String>(DemandClaimActivity.this) {
                        @Override
                        public void onNext(String s) {
                            ARouter.getInstance().build(HomeProtocol.SUBMITIDEA).withInt("type", 2).navigation();
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissLoadingDialog();
                            if (e instanceof LocalException) {
                                LocalException exception = (LocalException) e;
                                if (exception.getMsg().equals("plan_detail不能为空")) {
                                    ToastUtils.showShort("方案详情不能为空");
                                } else if (exception.getMsg().equals("plan_detail长度不符合要求 15,20000")) {
                                    ToastUtils.showShort("请至少填写15字以上描述");
                                } else {
                                    super.onError(e);
                                }
                            }
                        }

                    });
                } else {
                    HomeServiceFactory.rePostplan(plan_uuid, detailsEt.getText().toString().trim(), images).safeSubscribe(new AbstractDialogSubscriber<String>(DemandClaimActivity.this) {
                        @Override
                        public void onNext(String s) {
                            ARouter.getInstance().build(HomeProtocol.SUBMITIDEA).withInt("type", 2).navigation();
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof LocalException) {
                                dismissLoadingDialog();
                                LocalException exception = (LocalException) e;
                                if (exception.getMsg().equals("plan_detail不能为空")) {
                                    ToastUtils.showShort("方案详情不能为空");
                                } else if (exception.getMsg().equals("plan_detail长度不符合要求15,20000")) {
                                    ToastUtils.showShort("请至少填写15字以上描述");
                                } else {
                                    super.onError(e);
                                }
                            }
                        }
                    });
                }

            }
        });


        addImg.setOnClickListener(v -> beginCameraDialog());
        detailsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sizeDetails.setText(detailsEt.getText().toString().trim().length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgList.setLayoutManager(linearLayoutManager);
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

            appCompatButton.setOnClickListener(this);
            appCompatButton1.setOnClickListener(this);
            appCompatButton2.setOnClickListener(this);
        }
    }


    private void initView() {
        submitBtn = findViewById(R.id.submit_btn);
        backImg = findViewById(R.id.back_img);
        demandPrice = findViewById(R.id.demand_price);
        releaseTime = findViewById(R.id.re_date);
        demandTitle = findViewById(R.id.demand_title);
        addImg = findViewById(R.id.add_img);
        imgList = findViewById(R.id.img_list);
        sizeDetails = findViewById(R.id.size_details);
        detailsEt = findViewById(R.id.details_et);
        demand_content = findViewById(R.id.demand_content);
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
            uploadQiniu(result.get(result.size()-1), this);
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


    //图片上传至七牛
    public static void uploadQiniu(Uri uri, Context context) {
        final String[] path = {null};
        TipDialog loadingDialog;

        TipDialog.Builder builder = new TipDialog.Builder(context);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("图片上传中").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(context, uri) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("图片添加成功").create(500);
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
    public void onClick(View v) {
        int i=v.getId();
        if(i==R.id.photodialog_btn_take){
            goCamera();
            DIALOG.cancel();
        }else if(i==R.id.photodialog_btn_native){
            CheckImage.takePhotoPicker(DemandClaimActivity.this,1);
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
            uri = FileProvider.getUriForFile(DemandClaimActivity.this, "cn.dankal.basiclib.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        DemandClaimActivity.this.startActivityForResult(intent, ResultCode.TakeImageCode);
    }
}
