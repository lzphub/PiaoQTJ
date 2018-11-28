package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import api.UserServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyRequestPresenter implements RequestContact.idPresenter {

    private RequestContact.RequestView requestView;
    private static MyRequestPresenter myRequestPresenter = null;
    private MyRequestBean myRequestBean=null;

    public static synchronized MyRequestPresenter getPSPresenter() {
        if (myRequestPresenter == null) {
            myRequestPresenter = new MyRequestPresenter();
        }
        return myRequestPresenter;
    }

    @Override
    public void getData(String status,int page_index,int page_size) {
        MyServiceFactory.getMyRequest(status,page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<MyRequestBean>(requestView) {
            @Override
            public void onNext(MyRequestBean myRequestBean) {
                requestView.getDataSuccess(myRequestBean);
            }
        });
    }

    @Override
    public void delete(String status,String demand_id) {
        MyServiceFactory.deleteMyRequest(demand_id).safeSubscribe(new AbstractDialogSubscriber<String>(requestView) {
            @Override
            public void onNext(String s) {
                upData(status,1,10);
            }
        });
    }

    @Override
    public void getRequestData(String demand_id) {
        MyServiceFactory.getMyRequestData(demand_id).safeSubscribe(new AbstractDialogSubscriber<RequestDataBean>(requestView) {
            @Override
            public void onNext(RequestDataBean requestDataBean) {
                requestView.getRequestDataSuccess(requestDataBean);
            }
        });
    }

    private void upData(String status,int page_index,int page_size){
        MyServiceFactory.getMyRequest(status,page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<MyRequestBean>(requestView) {
            @Override
            public void onNext(MyRequestBean myRequestBean) {
               requestView.updata(myRequestBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull RequestContact.RequestView view) {
        requestView = view;
    }

    @Override
    public void detachView() {
        requestView = null;
    }
}
