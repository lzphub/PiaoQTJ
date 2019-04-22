package com.example.module_product.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.module_product.R;

import java.util.List;

import api.TaskServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.api.BaseApi;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.TaskBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;

import static cn.xunzi.basiclib.protocol.TaskProtocol.NEWBLETASK;

@Route(path = NEWBLETASK)
public class NewbieTaskActivity extends BaseActivity {
    private RelativeLayout title;
    private ImageView ivBack;
    private ImageView ivErweima;
    private TextView btSaoma;

    private int REQUEST_CODE_SCAN=23;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newbie_task;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        TaskServiceFactory.getTaskList(XZUserManager.getUserInfo().getId()+"","1").safeSubscribe(new AbstractDialogSubscriber<TaskBean>(NewbieTaskActivity.this) {
            @Override
            public void onNext(TaskBean taskBean) {
                Glide.with(NewbieTaskActivity.this).load(BaseApi.IMAGE_URL+taskBean.getData().getList().get(0).getImg()).into(ivErweima);
            }
        });
    }

    private void initView() {
        title = findViewById(R.id.title);
        ivBack = findViewById(R.id.iv_back);
        ivErweima = findViewById(R.id.iv_erweima);
        btSaoma = findViewById(R.id.bt_saoma);
    }
}
