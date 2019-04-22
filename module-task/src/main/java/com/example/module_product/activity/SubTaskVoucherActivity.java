package com.example.module_product.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.module_product.R;
import com.example.module_product.presenter.SubTaskVoucherConract;
import com.example.module_product.presenter.SubTaskVoucherPresenter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.TaskServiceFactory;
import cn.xunzi.basiclib.ResultCode;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.TaskDetaBean;
import cn.xunzi.basiclib.bean.TaskRecordBean;
import cn.xunzi.basiclib.bean.UpLoadBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ImagePathUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.util.image.CheckImage;

import static cn.xunzi.basiclib.protocol.TaskProtocol.SUBTASKVOUCHER;

@Route(path = SUBTASKVOUCHER)
public class SubTaskVoucherActivity extends BaseActivity implements View.OnClickListener, SubTaskVoucherConract.mtView {

    private ImageView ivBack;
    private TextView tvTime;
    private TextView tvTitle;
    private TextView tvMoney;
    private RelativeLayout rlAddimage;
    private ImageView ivAdd;
    private Button btSubmit;
    private TextView tvStatus;

    private AlertDialog DIALOG;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String photoPath;
    private List<Uri> result = new ArrayList<>();
    private int size = 1;
    private ImageView imImage;
    private SubTaskVoucherPresenter taskVoucherPresenter;
    private TaskRecordBean.DataEntity dataEntity;

    @Override
    protected int getLayoutId() {
        dataEntity = (TaskRecordBean.DataEntity) getIntent().getSerializableExtra("data");
        return R.layout.activity_sub_task_voucher;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        tvTime.setText(dataEntity.getUpdateTime());
        tvMoney.setText("¥" + dataEntity.getTaskReward());
        tvTitle.setText(dataEntity.getTaskTitle());

        if (dataEntity.getStatus() == 1) {
            rlAddimage.setBackgroundResource(R.mipmap.shenhezhong);
            ivAdd.setVisibility(View.GONE);
            btSubmit.setVisibility(View.GONE);
            tvStatus.setText("待审核...");
            tvStatus.setVisibility(View.VISIBLE);
            rlAddimage.setEnabled(false);
            imImage.setVisibility(View.GONE);
        } else if (dataEntity.getStatus() == 2) {
            rlAddimage.setBackgroundResource(R.mipmap.wancheng);
            ivAdd.setVisibility(View.GONE);
            btSubmit.setVisibility(View.GONE);
            tvStatus.setText("已完成");
            tvStatus.setVisibility(View.VISIBLE);
            rlAddimage.setEnabled(false);
            imImage.setVisibility(View.GONE);
        }else if(dataEntity.getStatus()==3){
            rlAddimage.setBackgroundResource(R.mipmap.shenhezhong);
            ivAdd.setVisibility(View.GONE);
            btSubmit.setVisibility(View.GONE);
            tvStatus.setText("已失效");
            tvStatus.setVisibility(View.VISIBLE);
            rlAddimage.setEnabled(false);
            imImage.setVisibility(View.GONE);
        }

        rlAddimage.setOnClickListener(view -> beginCameraDialog());

        btSubmit.setOnClickListener(view -> {
            if (result.size() != 1) {
                ToastUtils.showShort("请先添加图片");
                return;
            }
            taskVoucherPresenter = new SubTaskVoucherPresenter();
            taskVoucherPresenter.attachView(SubTaskVoucherActivity.this);
            String s = ImagePathUtil.getImageAbsolutePath(getApplicationContext(), result.get(0));
            taskVoucherPresenter.uploadImage(uriToFile(result.get(0), getApplicationContext()).toString(), dataEntity.getTaskId() + "");
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvTime = findViewById(R.id.tv_time);
        tvTitle = findViewById(R.id.tv_title);
        tvMoney = findViewById(R.id.tv_money);
        rlAddimage = findViewById(R.id.rl_addimage);
        ivAdd = findViewById(R.id.iv_add);
        btSubmit = findViewById(R.id.bt_submit);
        tvStatus = findViewById(R.id.tv_status);
        imImage = findViewById(R.id.im_image);
    }

    public void beginCameraDialog() {

        DIALOG = new AlertDialog.Builder(this).create();
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(cn.xunzi.basiclib.R.layout.dialog_photo_picker);
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

            AppCompatButton appCompatButton = window.findViewById(cn.xunzi.basiclib.R.id.photodialog_btn_cancel);
            AppCompatButton appCompatButton1 = window.findViewById(cn.xunzi.basiclib.R.id.photodialog_btn_take);
            AppCompatButton appCompatButton2 = window.findViewById(cn.xunzi.basiclib.R.id.photodialog_btn_native);

            appCompatButton.setOnClickListener(this);
            appCompatButton1.setOnClickListener(this);
            appCompatButton2.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.photodialog_btn_take) {
            AndPermission.with(this).runtime().permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE).onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                    goCamera();
                }
            }).start();
            DIALOG.cancel();
        } else if (i == R.id.photodialog_btn_native) {
            CheckImage.takePhotoPicker(SubTaskVoucherActivity.this, 1);
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
            uri = FileProvider.getUriForFile(SubTaskVoucherActivity.this, "cn.xunzi.basiclib.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        SubTaskVoucherActivity.this.startActivityForResult(intent, ResultCode.TakeImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultCode.CheckImageCode) {
            if (data != null) {
                result = new ArrayList<>();
                result.add(Matisse.obtainResult(data).get(0));
                if (result.size() == size) {
                    ivAdd.setVisibility(View.GONE);
                }
                Glide.with(this).load(result.get(0)).into(imImage);
            }
            return;
        }

        if (requestCode == ResultCode.TakeImageCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            result = new ArrayList<>();
            result.add(Uri.parse("file://" + photoPath));
            if (result.size() == size) {
                ivAdd.setVisibility(View.GONE);
            }
            Glide.with(this).load(result.get(0)).into(imImage);
        }
        return;
    }

    public static File uriToFile(Uri uri, Context context) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
        }
        return null;
    }

    @Override
    public void uploadSuccess(PostBean postBean) {
        finish();
        ToastUtils.showShort("提交成功");
    }
}
