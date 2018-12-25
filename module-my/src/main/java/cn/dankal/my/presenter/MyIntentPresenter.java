package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.GetIntentionBean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;

public class MyIntentPresenter implements MyIntentContact.fcPersenter {

    private MyIntentContact.fcView fcView;

    @Override
    public void getData(GetIntentionBean getIntentionBean) {
        MyServiceFactory.getIntentionList(JSON.toJSONString(getIntentionBean.getStatus()), getIntentionBean.getPage_index(), getIntentionBean.getPage_size()).safeSubscribe(new AbstractDialogSubscriber<MyIntentionBean>(fcView) {
            @Override
            public void onNext(MyIntentionBean myIntentionBean) {
                fcView.getDataSuccess(myIntentionBean);
            }

            @Override
            public void onError(Throwable e) {
                fcView.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("网络错误")) {
                        ToastUtils.showShort("Network error");
                    }
                }
            }
        });
    }

    @Override
    public void addData(GetIntentionBean getIntentionBean) {
        MyServiceFactory.getIntentionList(JSON.toJSONString(getIntentionBean.getStatus()), getIntentionBean.getPage_index(), getIntentionBean.getPage_size()).safeSubscribe(new AbstractDialogSubscriber<MyIntentionBean>(fcView) {
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
        fcView = view;
    }

    @Override
    public void detachView() {
        fcView = null;
    }
}
