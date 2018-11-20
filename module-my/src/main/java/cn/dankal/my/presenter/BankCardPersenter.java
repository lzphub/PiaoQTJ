package cn.dankal.my.presenter;

import android.support.annotation.NonNull;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BankCardPersenter implements BankCardContact.idPresenter,SmsCode{

    private BankCardContact.bcView bcView;
    private Disposable mDisposable;

    private static BankCardPersenter bankCardPersenter=null;
    public static BankCardPersenter getBankCardPersenter(){
        if(bankCardPersenter==null){
            bankCardPersenter=new BankCardPersenter();
        }
        return bankCardPersenter;
    }

    @Override
    public void sendCode(Button button, String phone) {
        MyServiceFactory.bankCardCode(phone).safeSubscribe(new AbstractDialogSubscriber<String>(bcView) {
            @Override
            public void onNext(String s) {
                sendCodeSuccess(button);
            }
        });
    }


    @Override
    public void bindCard(String card_number, String cardholer, String id_card_number, String reserved_mobile, String bank_name,String code) {

        MyServiceFactory.bindBankCard(card_number, cardholer, id_card_number, reserved_mobile,bank_name, code).safeSubscribe(new AbstractDialogSubscriber<String>(bcView) {
            @Override
            public void onNext(String s) {
                bcView.bindCardSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                bcView.bindCardFail();
            }
        });
    }

    @Override
    public void getBankCardList() {
        MyServiceFactory.getBankCardList().safeSubscribe(new AbstractDialogSubscriber<BankCardListBean>(bcView) {
            @Override
            public void onNext(BankCardListBean bankCardListBean) {
                bcView.getBankCardListSuccess(bankCardListBean);
            }
        });
    }

    @Override
    public void deleteCard(String cardNumber) {
        MyServiceFactory.deletBankCard(cardNumber).safeSubscribe(new AbstractDialogSubscriber<String>(bcView) {
            @Override
            public void onNext(String s) {
                getBankCardList();
            }
        });
    }

    @Override
    public void attachView(@NonNull BankCardContact.bcView view) {
        bcView=view;
    }

    @Override
    public void detachView() {
        bcView=null;
    }

    @Override
    public void getCode(String phone, Button mBtCode, String type) {

    }

    @Override
    public void sendCodeSuccess(Button mBtCode) {
        mBtCode.setEnabled(false);
        //倒计时
        mDisposable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    mBtCode.setText("已发送(" + (60 - aLong) + ")");
                })
                .doOnComplete(() -> {
                    mBtCode.setEnabled(true);
                    mBtCode.setText("获取验证码");
                }).subscribe();
    }

    @Override
    public void onDestory() {
        if (mDisposable != null) mDisposable.dispose();
    }

    @Override
    public void engGetCode(String phone, Button mBtCode, String type) {

    }

    @Override
    public void bankCardCode(String phone, Button mBtCode, String type) {

    }
}
