package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import api.MyServiceFactory;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.NewServiceMsgBean;
import cn.dankal.basiclib.bean.ProjectDataBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;

public class ProjectDetaPersenter implements ProjectDetaContact.productPresenter {

    private ProjectDetaContact.pcview pcview;

    @Override
    public void getProjectData(String projectId) {
        HomeServiceFactory.getDemandDeta(projectId).safeSubscribe(new AbstractDialogSubscriber<ProjectDataBean>(pcview) {
            @Override
            public void onNext(ProjectDataBean projectDataBean) {
                pcview.getProjectDataSuccess(projectDataBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void attachView(@NonNull ProjectDetaContact.pcview view) {
        pcview=view;
    }

    @Override
    public void detachView() {
        pcview=null;
    }
}
