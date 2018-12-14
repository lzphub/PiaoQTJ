package cn.dankal.user.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.sms.SmsCode;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.pojo.CheckCode;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.user.R;
import cn.dankal.user.presenter.UserRegisterPresenter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.dankal.basiclib.protocol.LoginProtocol.REGISTERVECODE;
import static cn.dankal.basiclib.protocol.LoginProtocol.USERREGISTERVECODE;

@Route(path = USERREGISTERVECODE)
public class UserRegistrCodeActivity extends BaseActivity {


    private ImageView backImg;
    private TextView tips;
    private TextView tvPhoneNum;
    private Button getVeCode;
    private EditText etEmailNum;
    private View dividerPhone;
    private Button btNext;
    private TextView titleText;

    private SmsCode smsCode;
    private String type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr_code;
    }

    @Override
    protected void initComponents() {
        initView();
        smsCode = new UserRegisterPresenter(this);
        titleText.setText("FAST REGISTEATION");
        tvPhoneNum.setText("CAPTCHA");
        getVeCode.setText("GET CODE");
        btNext.setText("COUNTINUE");
        etEmailNum.setHint("PLEASE INPUT THE PASSWORD");
        tips.setText("PLEASE ENTER THE VERIFICATION CODE RECEIVED BY " + getIntent().getStringExtra("emailAccount"));
        type = getIntent().getStringExtra("type");
        backImg.setOnClickListener(v -> finish());
        getVeCode.setOnClickListener(v -> smsCode.getCode(getIntent().getStringExtra("emailAccount"), getVeCode, type));
        btNext.setOnClickListener(v -> checkCode(getIntent().getStringExtra("emailAccount"), type, etEmailNum.getText().toString().trim()));
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
                    ARouter.getInstance().build(LoginProtocol.USERSETPWD).withString("emailAccount", getIntent().getStringExtra("emailAccount")).withString("type", type).navigation();
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    dismissLoadingDialog();
                    if (e instanceof LocalException) {
                        LocalException exception = (LocalException) e;
                        if(exception.getMsg().equals("验证码错误")){
                            ToastUtils.showShort("CODE ERROR");
                        }else if(exception.getMsg().equals("网络错误")){
                            ToastUtils.showShort("Network error");
                        }
                    }
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tips = findViewById(R.id.tips);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        getVeCode = findViewById(R.id.getVeCode);
        etEmailNum = findViewById(R.id.et_email_num);
        dividerPhone = findViewById(R.id.divider_phone);
        btNext = findViewById(R.id.bt_next);
        titleText = findViewById(R.id.title_text);
    }
}
