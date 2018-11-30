package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;

public class MyIntentPresenter implements  MyIntentContact.fcPersenter{

    private MyIntentContact.fcView fcView;

    @Override
    public void getData(String statusId, int page, int size) {
        MyServiceFactory.getIntentionList(statusId,page,20).safeSubscribe(new AbstractDialogSubscriber<MyIntentionBean>(fcView) {
            @Override
            public void onNext(MyIntentionBean myIntentionBean) {
                fcView.getDataSuccess(myIntentionBean);
            }
        });
    }

    @Override
    public void addData(String statusId, int page, int size) {
        MyServiceFactory.getIntentionList(statusId,page,20).safeSubscribe(new AbstractDialogSubscriber<MyIntentionBean>(fcView) {
            @Override
            public void onNext(MyIntentionBean myIntentionBean) {
                fcView.updata(myIntentionBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void attachView(@NonNull MyIntentContact.fcView view) {
        fcView=view;
    }

    @Override
    public void detachView() {
        fcView=null;
    }
}
