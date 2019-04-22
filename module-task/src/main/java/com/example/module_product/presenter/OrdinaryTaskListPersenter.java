package com.example.module_product.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.TaskServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.bean.TaskBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;

public class OrdinaryTaskListPersenter implements TaskListConract.myPresenter {

    private TaskListConract.mtView mtView;

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void getData(String userId) {
        TaskServiceFactory.getTaskList(XZUserManager.getUserInfo().getId()+"",status).safeSubscribe(new AbstractDialogSubscriber<TaskBean>(mtView) {
            @Override
            public void onNext(TaskBean taskBean) {
                if(taskBean.getCode().equals("200")){
                   mtView.getDataSuccess(taskBean);
                }else{
                    ToastUtils.showShort(taskBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void attachView(@NonNull TaskListConract.mtView view) {
        mtView=view;
    }

    @Override
    public void detachView() {
        mtView=null;
    }
}
