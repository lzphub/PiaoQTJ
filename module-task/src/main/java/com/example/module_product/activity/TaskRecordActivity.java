package com.example.module_product.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_product.R;
import com.example.module_product.presenter.RecordTaskListPersenter;

import cn.xunzi.basiclib.adapter.TaskRecordListAdapter;
import cn.xunzi.basiclib.base.BaseRvActivity;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.TaskRecordBean;

import static cn.xunzi.basiclib.protocol.TaskProtocol.TASKRECORD;

@Route(path = TASKRECORD)
public class TaskRecordActivity extends BaseRvActivity<TaskRecordBean.DataEntity> {

    private ImageView ivBack;
    private TaskRecordListAdapter taskRecordListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_record;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        ivBack.setOnClickListener(view -> finish());
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
    }

    @Override
    public BaseRecyclerViewPresenter<TaskRecordBean.DataEntity> getPresenter() {
        return new RecordTaskListPersenter();
    }

    @Override
    public BaseRecyclerViewAdapter<TaskRecordBean.DataEntity> getAdapter() {
        taskRecordListAdapter=new TaskRecordListAdapter();
        taskRecordListAdapter.setOnRvItemClickListener(new OnRvItemClickListener<TaskRecordBean.DataEntity>() {
            @Override
            public void onItemClick(View v, int position, TaskRecordBean.DataEntity data) {

            }
        });
        return taskRecordListAdapter;
    }
}
