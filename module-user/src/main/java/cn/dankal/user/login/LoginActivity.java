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

import api.UserServiceFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.pojo.UserResponseBody;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.PreferenceUtil;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.SpannableStringUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
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

    private String email,pwd;

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
                ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
                finish();
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
                SharedPreferencesUtils.saveString(LoginActivity.this, "identity", "user");
                finish();
//               email=etPhoneNum.getText().toString().trim();
//               if(!StringUtil.checkEmail(email)){
//                   ToastUtils.showShort("Wrong account number or password");
//                   return;
//               }
//               pwd=etPasswd.getText().toString().trim();
//               if(pwd!=null){
//                   ToastUtils.showShort("Wrong account number or password");
//                   return;
//               }
//               userLogin(email,pwd);
            }
        });
        register.setOnClickListener(v -> ARouter.getInstance().build(LoginProtocol.REGISTERUSER).navigation());
    }

    private void userLogin(String email, String pwd) {
      UserServiceFactory.login(email,pwd).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody>(this) {
          @Override
          public void onNext(UserResponseBody userResponseBody) {
              ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
              SharedPreferencesUtils.saveString(LoginActivity.this, "identity", "user");
              finish();
          }
      });
    }

    private void initView() {
        login = findViewById(R.id.login);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_phone_num);
        dividerPhone = findViewById(R.id.divider_phone);
        passwd = findViewById(R.id.passwd);
        etPasswd = findViewById(R.id.et_passwd);
        dividerPasswd = findViewById(R.id.divider_passwd);
        btLogin = findViewById(R.id.bt_login);
        enterpriseLogin = findViewById(R.id.enterprise_login);
        or = findViewById(R.id.or);
        register = findViewById(R.id.register);
        forgetPassword = findViewById(R.id.forget_password);
    }
}
