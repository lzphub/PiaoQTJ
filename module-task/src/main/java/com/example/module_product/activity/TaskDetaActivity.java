package com.example.module_product.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.module_product.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import api.TaskServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.TaskImg_Rec_Adapter;
import cn.xunzi.basiclib.api.BaseApi;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.TaskDetaBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;

import static cn.xunzi.basiclib.protocol.TaskProtocol.TASKDETA;

@Route(path = TASKDETA)
public class TaskDetaActivity extends BaseActivity {

    private ImageView imBack;
    private TextView tvTime;
    private TextView tvTitle;
    private TextView tvMoney;
    private TextView tvTaskdeta;
    private Button btCopy;
    private Button btPreimage;
    private Button btGetTask;
    private Context context;
    private String taskId = "3";
    private String status;
    private RecyclerView ivImg;
    private String imgurl;
    private TaskImg_Rec_Adapter taskImg_rec_adapter;
    private List<String> imgs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_deta;
    }

    @Override
    protected void initComponents() {
        initView();
        taskId = getIntent().getStringExtra("taskId");
        status = getIntent().getStringExtra("status");
        context = this;
        imBack.setOnClickListener(view -> finish());
        LinearLayoutManager ms = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        ivImg.setLayoutManager(ms);

        if (status.equals("1")) {
            btGetTask.setEnabled(false);
            btGetTask.setBackgroundResource(R.drawable.login_btn_bg_b7);
            btGetTask.setText("已领取");
        }

        TaskServiceFactory.getTaskDeta(taskId).safeSubscribe(new AbstractDialogSubscriber<TaskDetaBean>(TaskDetaActivity.this) {
            @Override
            public void onNext(TaskDetaBean taskDetaBean) {
                if (taskDetaBean.getCode().equals("200")) {
                    tvTitle.setText(taskDetaBean.getData().getTitle());
                    tvMoney.setText("¥" + taskDetaBean.getData().getReward());
                    tvTime.setText(taskDetaBean.getData().getUpdateTime());
                    tvTaskdeta.setText(taskDetaBean.getData().getDetail());
                    imgurl = BaseApi.IMAGE_URL + taskDetaBean.getData().getImg();
                    String[] imgurl = taskDetaBean.getData().getImg().split(",");
                    imgs = new ArrayList<>();
                    for (int i = 0; i < imgurl.length; i++) {
                        imgs.add(imgurl[i]);
                    }
                    taskImg_rec_adapter = new TaskImg_Rec_Adapter();
                    taskImg_rec_adapter.updateData(imgs);
                    ivImg.setAdapter(taskImg_rec_adapter);
                }
            }
        });

        btGetTask.setOnClickListener(view -> TaskServiceFactory.receiveTask(XZUserManager.getUserInfo().getId() + "", taskId).safeSubscribe(new AbstractDialogSubscriber<PostBean>(TaskDetaActivity.this) {
            @Override
            public void onNext(PostBean postBean) {
                ToastUtils.showShort(postBean.getMsg());
                if (postBean.getCode().equals("200")) {
                    btGetTask.setEnabled(false);
                    btGetTask.setText("已领取");
                    btGetTask.setBackgroundResource(R.drawable.login_btn_bg_b7);
                }
            }
        }));

        btCopy.setOnClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(tvTaskdeta.getText());
            Toast toast = Toast.makeText(TaskDetaActivity.this, "已复制到粘贴板", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 550);
            toast.show();
        });

        btPreimage.setOnClickListener(view -> {
            AndPermission.with(this).runtime().permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE).onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                    for (int i = 0; i < imgs.size(); i++) {
                        addTask(BaseApi.IMAGE_URL+imgs.get(i));
                    }
                }
            }).start();
        });
    }

    //  异步任务  直接传图片地址
    public void addTask(String url) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                // 后台通信
                return decodeBitmap(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                // 主线程处理view
                if (bitmap != null) {
                    //保存
                    saveImageToGallery(context, bitmap);
                }
            }
        }.execute(url);
    }

    /**
     * url  到  bitmap
     *
     * @param httpUrl
     * @return
     */
    private Bitmap decodeBitmap(String httpUrl) {
        URL url = null;
        Bitmap bm = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = url.openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 保存图片到图库的方法
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "pqtj");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            Toast toast = Toast.makeText(context, "已保存至相册", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 1100);
            toast.show();
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }


    private void initView() {
        imBack = findViewById(R.id.im_back);
        tvTime = findViewById(R.id.tv_time);
        tvTitle = findViewById(R.id.tv_title);
        tvMoney = findViewById(R.id.tv_money);
        tvTaskdeta = findViewById(R.id.tv_taskdeta);
        btCopy = findViewById(R.id.bt_copy);
        btPreimage = findViewById(R.id.bt_preimage);
        btGetTask = findViewById(R.id.bt_getTask);
        ivImg = findViewById(R.id.iv_img);
    }
}
