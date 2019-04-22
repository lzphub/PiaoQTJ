package cn.xunzi.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.bean.WithBean;
import cn.xunzi.basiclib.bean.WithDetaBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;

public class CashInPresenter implements CashInConract.myPresenter {

    private CashInConract.mtView mtView;



    @Override
    public void getData(String UserId, String state) {
        MyServiceFactory.withRecorde(XZUserManager.getUserInfo().getId() + "", state).safeSubscribe(new AbstractDialogSubscriber<WithDetaBean>(mtView) {
            @Override
            public void onNext(WithDetaBean withDetaBean) {
                if(withDetaBean.getCode().equals("200")){
                    mtView.getDataSuccess(withDetaBean);
                }else{
                    mtView.getDataFail();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mtView.getDataFail();
            }
        });
    }

    @Override
    public void attachView(@NonNull CashInConract.mtView view) {
        mtView = view;
    }

    @Override
    public void detachView() {
        mtView = null;
    }
}
