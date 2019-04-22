package cn.xunzi.basiclib.common.sms;

import android.widget.Button;

import java.util.concurrent.TimeUnit;

import cn.xunzi.basiclib.base.BaseView;
import cn.xunzi.basiclib.util.StringUtil;
import cn.xunzi.basiclib.util.ToastUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Date: 2018/8/1.
 * Time: 12:04
 * classDescription:
 *
 * @author fred
 */
public class SmsCodeImpl implements SmsCode {
    private Disposable mDisposable;

    private final BaseView baseView;
    /**
     * 找回密码
     */
    public static final String TYPE_PASS = "pass";
    /**
     * 设置支付密码
     */
    public static final String TYPE_SET_PAY = "set_pay";
    /**
     * 绑定银行卡
     */
    public static final String TYPE_BIND_CARD = "bind_card";


    public SmsCodeImpl(BaseView baseView) {
        this.baseView=baseView;
    }

    @Override
    public void getCode(String phone, Button mBtCode, String type) {
        if (!StringUtil.checkPhone(phone)) {
            ToastUtils.showShort("无效手机号码");
            return;
        }
     /*   UserServiceFactory.obtainVerifyCode(phone,type)
                .subscribe(new AbstractDialogSubscriber<String>(baseView) {
                    @Override
                    public void onNext(String s) {
                        sendCodeSuccess(mBtCode);
                    }
                });*/
    }

    @Override
    public void sendCodeSuccess(Button mBtCode) {
        mBtCode.setEnabled(false);
        //倒计时
        mDisposable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                    mBtCode.setText("重新获取(" + (60 - aLong) + ")");
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
