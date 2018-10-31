package cn.dankal.user.login;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.user.R;
import android.view.*;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import static cn.dankal.basiclib.protocol.LoginProtocol.FORGETPWDSET;
import static cn.dankal.basiclib.util.AppUtils.finishAllActivities;

@Route(path = FORGETPWDSET)
public class ForgetPwdSetActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.view.View dividerPhone;
    private android.widget.TextView passwd;
    private android.widget.EditText etPasswd;
    private android.view.View dividerPasswd;
    private android.widget.Button btNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd;
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
                Toast.makeText(ForgetPwdSetActivity.this, getResources().getString(R.string.setpwdOk), Toast.LENGTH_SHORT).show();
                finishAllActivities();
                ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        passwd = (TextView) findViewById(R.id.passwd);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        dividerPasswd = (View) findViewById(R.id.divider_passwd);
        btNext = (Button) findViewById(R.id.bt_next);
    }
}
