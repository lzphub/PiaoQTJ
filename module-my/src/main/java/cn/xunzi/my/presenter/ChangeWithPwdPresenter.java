package cn.xunzi.my.presenter;

import android.graphics.Color;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import api.UserServiceFactory;
import cn.xunzi.basiclib.base.BaseView;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.common.sms.SmsCode;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.StringUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChangeWithPwdPresenter implements SmsCode {

    private Disposable mDisposable;
    private final BaseView baseView;

    public ChangeWithPwdPresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void getCode(String phone, Button mBtCode, String type) {
        if (StringUtil.checkPhone(phone)) {
            UserServiceFactory.getCode(phone, type)
                    .subscribe(new AbstractDialogSubscriber<PostBean>(baseView) {
                        @Override
                        public void onNext(PostBean postBean) {
                            if (postBean.getCode().equals("200")) {
                                sendCodeSuccess(mBtCode);
                            }else{
                                baseView.dismissLoadingDialog();
                                ToastUtils.showShort(postBean.getMsg());
                            }
                        }
                    });
        } else {
            ToastUtils.showShort("请填写正确的手机号");
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
                    mBtCode.setTextColor(Color.parseColor("#CFCFCF"));
                })
                .doOnComplete(() -> {
                    mBtCode.setEnabled(true);
                    mBtCode.setText("获取验证码");
                    mBtCode.setTextColor(Color.parseColor("#FF5000"));
                }).subscribe();
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void engGetCode(String phone, Button mBtCode, String type) {

    }

    @Override
    public void bankCardCode(String phone, Button mBtCode, String type) {

    }
}
