package com.example.module_product.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_product.R;
import com.example.module_product.presenter.OrdinaryTaskListPersenter;
import com.example.module_product.presenter.TaskListConract;

import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.TaskListAdapter;
import cn.xunzi.basiclib.base.BaseRvActivity;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.TaskBean;
import cn.xunzi.basiclib.protocol.TaskProtocol;
import cn.xunzi.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.xunzi.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.xunzi.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;

import static cn.xunzi.basiclib.protocol.TaskProtocol.TASKLIST;

@Route(path = TASKLIST)
public class TaskListActivity extends BaseActivity implements TaskListConract.mtView {

    private ImageView ivBack;
    private int title;
    private TextView tvTitle;
    private TaskListAdapter taskListAdapter;
    private OrdinaryTaskListPersenter persenter;
    private SwipeToLoadLayout swipeToloadLayout;
    private RecyclerView swipeTarget;
    private boolean isre=false;

    @Override
    protected int getLayoutId() {
        title=getIntent().getIntExtra("title",0);
        return R.layout.activity_task_list;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());

        swipeToloadLayout.setLoadMoreEnabled(false);

        swipeToloadLayout.setOnRefreshListener(() -> {
            persenter=new OrdinaryTaskListPersenter();
            persenter.attachView(TaskListActivity.this);
            persenter.setStatus(title+"");
            persenter.getData(XZUserManager.getUserInfo().getId()+"");
            isre=true;
        });

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));

        if(title==3){
            tvTitle.setText("高级任务");
        }else{
            tvTitle.setText("普通任务");
        }
    }


    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeTarget = findViewById(R.id.swipe_target);
    }

    @Override
    protected void onResume() {
        super.onResume();
        persenter=new OrdinaryTaskListPersenter();
        persenter.attachView(this);
        persenter.setStatus(title+"");
        persenter.getData(XZUserManager.getUserInfo().getId()+"");
    }

    @Override
    public void getDataSuccess(TaskBean taskBean) {
        taskListAdapter=new TaskListAdapter();
        taskListAdapter.updateData(taskBean.getData().getList());
        swipeTarget.setAdapter(taskListAdapter);
        taskListAdapter.setOnRvItemClickListener(new OnRvItemClickListener<TaskBean.DataEntity.ListEntity>() {
            @Override
            public void onItemClick(View v, int position, TaskBean.DataEntity.ListEntity data) {
                ARouter.getInstance().build(TaskProtocol.TASKDETA).withString("taskId",data.getId()+"").withString("status",data.getUserStatue()+"").navigation();
            }
        });
        if(isre){
            swipeToloadLayout.setRefreshing(false);
            isre=false;
        }
    }
}
