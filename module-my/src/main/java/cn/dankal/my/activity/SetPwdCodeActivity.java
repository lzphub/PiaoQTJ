package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.concurrent.TimeUnit;

import api.MyServiceFactory;
import api.UserServiceFactory;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.PersonalData_EngineerBean;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.pojo.CheckCode;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;
import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static cn.dankal.basiclib.protocol.MyProtocol.SETWITHPEDCODE;

/**
 * 设置提现密码获取验证码
 */
@Route(path = SETWITHPEDCODE)
public class SetPwdCodeActivity extends BaseActivity implements BaseView {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView tips;
    private android.widget.TextView tvPhoneNum;
    private android.widget.Button getVeCode;
    private android.widget.EditText etEmailNum;
    private android.widget.Button btNext;

    private Disposable mDisposable;
    private String email;
    private int code2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd_code;
    }

    @Override
    protected void initComponents() {
        initView();

        getData();

        code2 = getIntent().getIntExtra("type", 0);

        backImg.setOnClickListener(v -> finish());
        btNext.setOnClickListener(v -> checkCode(email, "withdrawal", etEmailNum.getText().toString().trim()));
        getVeCode.setOnClickListener(v -> UserServiceFactory.getCode(email, "withdrawal").safeSubscribe(new AbstractDialogSubscriber<String>(SetPwdCodeActivity.this) {
            @Override
            public void onNext(String s) {
                sendSuccess(getVeCode);
            }
        }));
    }

    private void sendSuccess(Button mBtCode) {
        mBtCode.setEnabled(false);
        //倒计时
        mDisposable = Flowable.intervalRange(1, 600, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(aLong -> {
            mBtCode.setText("OBTAIN(" + (60 - aLong) + ")");
        }).doOnComplete(() -> {
            mBtCode.setEnabled(true);
            mBtCode.setText("GET CODE");
        }).subscribe();
    }

    //检查验证码
    public void checkCode(String email, String type, String code) {
        if (StringUtil.checkEmail(email)) {
            UserServiceFactory.verifyCode(email, type, code).safeSubscribe(new Observer<CheckCode>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(CheckCode checkCode) {
                    ARouter.getInstance().build(MyProtocol.SETWITHPWD).withInt("type", code2).withString("email", email).navigation();
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    dismissLoadingDialog();
                    if (e instanceof LocalException) {
                        LocalException exception = (LocalException) e;
                        ToastUtils.showShort(exception.getMsg());
                    }
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }


    private void getData() {
        MyServiceFactory.getEngineerData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EngineerBean>(this) {
            @Override
            public void onNext(PersonalData_EngineerBean personalData_engineerBean) {
                tips.append(personalData_engineerBean.getEmail() + "的验证码");
                email = personalData_engineerBean.getEmail();
            }
        });
    }

    private void initView() {
        tips = findViewById(R.id.tips);
        btNext = findViewById(R.id.bt_next);
        backImg = findViewById(R.id.back_img);
        getVeCode = findViewById(R.id.getVeCode);
        titleText = findViewById(R.id.title_text);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etEmailNum = findViewById(R.id.et_email_num);
    }
}
