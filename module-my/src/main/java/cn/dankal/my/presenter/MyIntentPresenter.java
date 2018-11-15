package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyIntentPresenter extends BaseRecyclerViewPresenter<MyIntentionBean.DataBean>{

    @Override
    public void requestData(String pageIndex) {
        MyServiceFactory.getIntentionList(pageIndex).safeSubscribe(new AbstractDialogSubscriber<MyIntentionBean>(mView) {
            @Override
            public void onNext(MyIntentionBean myIntentionBean) {
                mView.render(myIntentionBean.getData());
            }
        });
    }


}
