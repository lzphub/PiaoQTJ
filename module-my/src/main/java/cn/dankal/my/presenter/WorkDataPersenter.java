package cn.dankal.my.presenter;

import android.support.annotation.NonNull;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import api.HomeServiceFactory;
import api.MyServiceFactory;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.MyWorkDataBean;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WorkDataPersenter implements WorkDataContact.idPresenter{

    private WorkDataContact.bcView bcView;
    private Disposable mDisposable;

    private static WorkDataPersenter workDataPersenter=null;
    public static WorkDataPersenter getBankCardPersenter(){
        if(workDataPersenter==null){
            workDataPersenter=new WorkDataPersenter();
        }
        return workDataPersenter;
    }


    @Override
    public void getWorkData(String uuid) {
        HomeServiceFactory.getWorkData(uuid).safeSubscribe(new AbstractDialogSubscriber<MyWorkDataBean>(bcView) {
            @Override
            public void onNext(MyWorkDataBean myWorkDataBean) {
                bcView.getWordDataSuccess(myWorkDataBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull WorkDataContact.bcView view) {
        bcView=view;
    }

    @Override
    public void detachView() {
        bcView=null;
    }
}
