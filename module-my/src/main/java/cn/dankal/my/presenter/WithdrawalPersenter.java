package cn.dankal.my.presenter;

import android.support.annotation.NonNull;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import api.MyServiceFactory;
import cn.dankal.basiclib.api.MyService;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WithdrawalPersenter implements WithdrawalContact.idPresenter{

    private WithdrawalContact.bcView bcView;

    private static WithdrawalPersenter withdrawalPersenter=null;
    public static WithdrawalPersenter getwithdrawalPersenter(){
        if(withdrawalPersenter==null){
            withdrawalPersenter=new WithdrawalPersenter();
        }
        return withdrawalPersenter;
    }

    @Override
    public void withDarawal(String withdrawal_password, String withdrawal_money, String bank_card_number) {
        MyServiceFactory.withdrawal(withdrawal_password, withdrawal_money, bank_card_number).safeSubscribe(new AbstractDialogSubscriber<String>(bcView) {
            @Override
            public void onNext(String s) {
                bcView.withDarawalSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                bcView.withDarawalFail();
            }
        });
    }

    @Override
    public void attachView(@NonNull WithdrawalContact.bcView view) {
        bcView=view;
    }

    @Override
    public void detachView() {
        bcView=null;
    }
}
