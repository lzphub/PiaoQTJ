package cn.dankal.user.login;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.user.R;
import android.widget.*;
import android.view.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import static cn.dankal.basiclib.protocol.LoginProtocol.FORGETPWDCODE;

@Route(path = FORGETPWDCODE)
public class ForgetPwdCodeActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView tips;
    private android.widget.TextView tvPhoneNum;
    private android.widget.Button getVeCode;
    private android.widget.EditText etEmailNum;
    private android.view.View dividerPhone;
    private android.widget.Button btNext;
    private TextView titleText;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr_code;
    }

    @Override
    protected void initComponents() {
        initView();
        titleText.setText(getResources().getString(R.string.forgetpwd));
        tips.append(getIntent().getStringExtra("emailAccount")+getResources().getString(R.string.DVerificationCode));
        type=getIntent().getStringExtra("type");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(LoginProtocol.FORGETPWDSET).navigation();
            }
        });
        getVeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
