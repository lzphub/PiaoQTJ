package cn.dankal.user.login;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.user.R;

import android.widget.*;
import android.view.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import static cn.dankal.basiclib.protocol.LoginProtocol.FORGETPWD;

@Route(path = FORGETPWD)
public class ForgetPwdActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etEmailNum;
    private android.view.View dividerPhone;
    private android.widget.Button btNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registr;
    }

    @Override
    protected void initComponents() {
        initView();
        titleText.setText(getResources().getString(R.string.forgetpwd));
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmailNum.getText().toString().trim().isEmpty()) {
                    ARouter.getInstance().build(LoginProtocol.FORGETPWDCODE).withString("emailAccount",etEmailNum.getText().toString().trim()).navigation();
                } else {
                    Toast.makeText(ForgetPwdActivity.this, "请输入正确的邮箱账号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etEmailNum = (EditText) findViewById(R.id.et_email_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        btNext = (Button) findViewById(R.id.bt_next);
    }
}
