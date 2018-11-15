package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import api.MyServiceFactory;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyIntentionDetailsPresenter implements IntentionDetailsContact.idPresenter {
    private IntentionDetailsContact.idView idView;
    private static MyIntentionDetailsPresenter myIntentionDetailsPresenter = null;

    public static synchronized MyIntentionDetailsPresenter getPSPresenter() {
        if (myIntentionDetailsPresenter == null) {
            myIntentionDetailsPresenter = new MyIntentionDetailsPresenter();
        }
        return myIntentionDetailsPresenter;
    }

    @Override
    public void getData(String intention_id) {
        MyServiceFactory.getIntentionInfo(intention_id).safeSubscribe(new AbstractDialogSubscriber<IntentionDateBean>(idView) {
            @Override
            public void onNext(IntentionDateBean intentionDateBean) {
                idView.getDataSuccess(intentionDateBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull IntentionDetailsContact.idView view) {
        idView=view;
    }

    @Override
    public void detachView() {
        idView=null;
    }
}
