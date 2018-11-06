package cn.dankal.user.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.util.PreferenceUtil;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.SpannableStringUtils;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.ENTERPRISELOGIN;
import static cn.dankal.basiclib.protocol.LoginProtocol.USERSLOGIN;

@Route(path = USERSLOGIN)
public class LoginActivity extends BaseActivity {

    private TextView login;
    private TextView tvPhoneNum;
    private EditText etPhoneNum;
    private View dividerPhone;
    private TextView passwd;
    private EditText etPasswd;
    private View dividerPasswd;
    private Button btLogin;
    private TextView enterpriseLogin;
    private TextView or;
    private TextView register;
    private TextView forgetPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents() {
        initView();
        enterpriseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
                finish();
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
                SharedPreferencesUtils.saveString(LoginActivity.this,"identity","user");
                finish();
            }
        });
    }

    private void initView() {
        login = (TextView) findViewById(R.id.login);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        etPhoneNum = (EditText) findViewById(R.id.et_phone_num);
        dividerPhone = (View) findViewById(R.id.divider_phone);
        passwd = (TextView) findViewById(R.id.passwd);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        dividerPasswd = (View) findViewById(R.id.divider_passwd);
        btLogin = (Button) findViewById(R.id.bt_login);
        enterpriseLogin = (TextView) findViewById(R.id.enterprise_login);
        or = (TextView) findViewById(R.id.or);
        register = (TextView) findViewById(R.id.register);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
    }
}
