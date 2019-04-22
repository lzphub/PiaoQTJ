package com.example.module_product.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_product.R;
import com.example.module_product.presenter.SubTaskListConract;
import com.example.module_product.presenter.SubTaskListPersenter;
import com.example.module_product.presenter.TaskListConract;

import java.io.File;

import cn.xunzi.basiclib.ResultCode;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.SubTaskListAdapter;
import cn.xunzi.basiclib.base.BaseRvActivity;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.SubTaskListBean;
import cn.xunzi.basiclib.bean.TaskBean;
import cn.xunzi.basiclib.bean.TaskRecordBean;
import cn.xunzi.basiclib.protocol.TaskProtocol;
import cn.xunzi.basiclib.util.image.CheckImage;
import cn.xunzi.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.xunzi.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;

import static cn.xunzi.basiclib.protocol.TaskProtocol.SUBMITTASK;

@Route(path = SUBMITTASK)
public class SubmitTaskActivity extends BaseActivity implements SubTaskListConract.mtView {

    private ImageView ivBack;
    private SubTaskListAdapter subTaskListAdapter;
    private SwipeToLoadLayout swipeToloadLayout;
    private RecyclerView swipeTarget;
    private SubTaskListPersenter subTaskListPersenter;
    private boolean isre=false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_task;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        swipeToloadLayout.setLoadMoreEnabled(false);
        swipeToloadLayout.setOnRefreshListener(() -> {
            isre=true;
            subTaskListPersenter=new SubTaskListPersenter();
            subTaskListPersenter.attachView(this);
            subTaskListPersenter.getData(XZUserManager.getUserInfo().getId()+"");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        subTaskListPersenter=new SubTaskListPersenter();
        subTaskListPersenter.attachView(this);
        subTaskListPersenter.getData(XZUserManager.getUserInfo().getId()+"");
    }

    private void initView() {
        ivBack=findViewById(R.id.iv_back);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeTarget = findViewById(R.id.swipe_target);
    }

    @Override
    public void getDataSuccess(TaskRecordBean taskRecordBean) {
        subTaskListAdapter=new SubTaskListAdapter();
        subTaskListAdapter.updateData(taskRecordBean.getData());
        swipeTarget.setAdapter(subTaskListAdapter);
        subTaskListAdapter.setOnRvItemClickListener(new OnRvItemClickListener<TaskRecordBean.DataEntity>() {
            @Override
            public void onItemClick(View v, int position, TaskRecordBean.DataEntity data) {
                ARouter.getInstance().build(TaskProtocol.SUBTASKVOUCHER).withSerializable("data",data).navigation();
            }
        });
        if(isre){
            swipeToloadLayout.setRefreshing(false);
            isre=false;
        }
    }
}
