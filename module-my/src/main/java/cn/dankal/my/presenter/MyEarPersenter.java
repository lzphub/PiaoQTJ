package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.MyEarBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyEarPersenter implements MyEarContact.imePresenter {

    private MyEarContact.meView meView;
    private static MyEarPersenter myEarPersenter;

    public static synchronized MyEarPersenter getPersenter(){
        if(myEarPersenter==null){
            myEarPersenter=new MyEarPersenter();
        }
        return myEarPersenter;
    }

    @Override
    public void attachView(@NonNull MyEarContact.meView view) {
        meView=view;
    }

    @Override
    public void detachView() {
        meView=null;
    }

    @Override
    public void getData() {
        MyServiceFactory.getMyEar().safeSubscribe(new AbstractDialogSubscriber<MyEarBean>(meView) {
            @Override
            public void onNext(MyEarBean myEarBean) {
                meView.getDataSuccess(myEarBean);
            }
        });
    }
}
