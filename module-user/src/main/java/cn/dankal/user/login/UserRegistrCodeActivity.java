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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr_code;
    }

    @Override
    protected void initComponents() {
        initView();
        smsCode=new UserRegisterPresenter(this);
        titleText.setText("FAST REGISTEATION");
        tvPhoneNum.setText("CAPTCHA");
        getVeCode.setText("GET CODE");
        btNext.setText("COUNTINUE");
        etEmailNum.setHint("PLEASE INPUT THE PASSWORD");
        tips.setText("PLEASE ENTER THE VERIFICATION CODE RECEIVED BY "+getIntent().getStringExtra("emailAccount"));
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getVeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsCode.getCode(getIntent().getStringExtra("emailAccount"),getVeCode,"sign_up");
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode(getIntent().getStringExtra("emailAccount"),"sign_up",etEmailNum.getText().toString().trim());
            }
        });
    }

    //检查验证码
    public void checkCode(String email, String type, String code) {
         if(StringUtil.checkEmail(email)){
            UserServiceFactory.verifyCode(email,type,code).safeSubscribe(new Observer<CheckCode>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(CheckCode checkCode) {
                    ARouter.getInstance().build(LoginProtocol.SETPWD).withString("emailAccount",getIntent().getStringExtra("emailAccount")).navigation();
                    finish();
                }

                @Override
                public void onError(Throwable e) {
//                    ToastUtils.showShort(e.toString());
                    ARouter.getInstance().build(LoginProtocol.SETPWD).withString("emailAccount",getIntent().getStringExtra("emailAccount")).navigation();
                    finish();
                }

                @Override
                public void onComplete() {

                }
            });
        }
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
