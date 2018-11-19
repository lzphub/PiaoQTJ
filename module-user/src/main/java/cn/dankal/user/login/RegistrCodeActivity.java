package cn.dankal.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.pojo.CheckCode;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.user.R;
import cn.dankal.user.presenter.UserRegisterPresenter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERVECODE;

@Route(path = REGISTERVECODE)
public class RegistrCodeActivity extends BaseActivity {


    private ImageView backImg;
    private TextView tips;
    private TextView tvPhoneNum;
    private Button getVeCode;
    private EditText etEmailNum;
    private View dividerPhone;
    private Button btNext;
    private SmsCode smsCode;
    private String type;
    private TextView titleText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr_code;
    }

    @Override
    protected void initComponents() {
        initView();
        smsCode=new UserRegisterPresenter(this);
        String s=getResources().getString(R.string.DVerificationCode);
        tips.append(getIntent().getStringExtra("emailAccount")+s);
        type=getIntent().getStringExtra("type");
        if(type.equals("change_pwd")){
            titleText.setText("忘记密码");
        }
        backImg.setOnClickListener(v -> finish());
        getVeCode.setOnClickListener(v ->
                smsCode.engGetCode(getIntent().getStringExtra("emailAccount"),getVeCode,type));
        btNext.setOnClickListener(v -> UserServiceFactory.verifyCode(getIntent().getStringExtra("emailAccount"),type,etEmailNum.getText().toString().trim()).safeSubscribe(new AbstractDialogSubscriber<CheckCode>(this) {
            @Override
            public void onNext(CheckCode checkCode) {
                ARouter.getInstance().build(LoginProtocol.SETPWD).withString("type",type).withString("emailAccount",getIntent().getStringExtra("emailAccount")).navigation();
            }
        }));
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tips = (TextView) findViewById(R.id.tips);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        getVeCode = (Button) findViewById(R.id.getVeCode);
        etEmailNum = (EditText) findViewById(R.id.et_email_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        btNext = (Button) findViewById(R.id.bt_next);
        titleText = (TextView) findViewById(R.id.title_text);
    }
}
