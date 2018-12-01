package cn.dankal.user.presenter;

import android.widget.Button;

import java.util.concurrent.TimeUnit;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.pojo.CheckCode;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.StringUtil;
import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class UserRegisterPresenter implements SmsCode {

    private Disposable mDisposable;

    private final BaseView baseView;

    public UserRegisterPresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void getCode(String email, Button mBtCode, String type) {
        if (StringUtil.checkEmail(email)) {
            UserServiceFactory.obtainVerifyCode(email, type)
                    .subscribe(new AbstractDialogSubscriber<String>(baseView) {
                        @Override
                        public void onNext(String s) {
                            sendEngCodeSuccess(mBtCode);
                        }
                    });
        } else {

        }
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
            if(StringUtil.checkEmail(phone)){
                UserServiceFactory.getCode(phone,type).safeSubscribe(new AbstractDialogSubscriber<String>(baseView) {
                    @Override
                    public void onNext(String s) {
                        sendCodeSuccess(mBtCode);
                    }
                });
            }
    }

    public void sendEngCodeSuccess(Button mBtCode) {
        mBtCode.setEnabled(false);
        //倒计时
        mDisposable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    mBtCode.setText("OBTAIN(" + (60 - aLong) + ")");
                })
                .doOnComplete(() -> {
                    mBtCode.setEnabled(true);
                    mBtCode.setText("GET CODE");
                }).subscribe();
    }


    @Override
    public void bankCardCode(String phone, Button mBtCode, String type) {

    }


}
