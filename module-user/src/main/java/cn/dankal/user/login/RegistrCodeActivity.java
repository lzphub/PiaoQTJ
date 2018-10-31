package cn.dankal.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.user.R;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr_code;
    }

    @Override
    protected void initComponents() {
        initView();
        String s=getResources().getString(R.string.DVerificationCode);
        tips.append(getIntent().getStringExtra("emailAccount")+s);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getVeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(LoginProtocol.SETPWD).navigation();
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
    }
}
