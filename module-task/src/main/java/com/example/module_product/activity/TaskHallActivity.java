package com.example.module_product.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_product.R;

import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.protocol.TaskProtocol;

import static cn.xunzi.basiclib.protocol.TaskProtocol.TASKDATING;

@Route(path = TASKDATING)
public class TaskHallActivity extends BaseActivity {

    private ImageView ivBack;
    private RelativeLayout rlPutongTask;
    private RelativeLayout rlGaiojiTask;
    private RelativeLayout rlXinshouTask;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_hall;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        rlGaiojiTask.setOnClickListener(view -> ARouter.getInstance().build(TaskProtocol.TASKLIST).withInt("title",3).navigation());
        rlPutongTask.setOnClickListener(view -> ARouter.getInstance().build(TaskProtocol.TASKLIST).withInt("title",2).navigation());
        rlXinshouTask.setOnClickListener(view -> ARouter.getInstance().build(TaskProtocol.NEWBLETASK).navigation());
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        rlPutongTask = findViewById(R.id.rl_putongTask);
        rlGaiojiTask = findViewById(R.id.rl_gaiojiTask);
        rlXinshouTask = findViewById(R.id.rl_xinshouTask);
    }
}
