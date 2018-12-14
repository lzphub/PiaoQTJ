package cn.dankal.user.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.pojo.UserResponseBody;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.ENTERPRISELOGIN;

@Route(path = ENTERPRISELOGIN)
public class EnterpriseLoginActivity extends BaseActivity {

    private android.widget.TextView login;
    private android.widget.TextView tvPhoneNum;
    private android.widget.EditText etPhoneNum;
    private android.view.View dividerPhone;
    private android.widget.TextView passwd;
    private android.widget.EditText etPasswd;
    private android.view.View dividerPasswd;
    private android.widget.Button btLogin;
    private android.widget.RelativeLayout rl;
    private android.widget.TextView usersLogin;
    private android.widget.TextView or;
    private android.widget.TextView register;
    private android.widget.TextView forgetPassword;
    private String email, pwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enterprise_login;
    }

    @Override
    protected void initComponents() {
        initView();
        usersLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
            finish();
        });
        register.setOnClickListener(v -> ARouter.getInstance().build(LoginProtocol.REGISTERENTEREMSIL).withString("type", "sign_up").navigation());
        forgetPassword.setOnClickListener(v -> ARouter.getInstance().build(LoginProtocol.REGISTERENTEREMSIL).withString("type", "change_pwd").navigation());
        btLogin.setOnClickListener(v -> {
            email = etPhoneNum.getText().toString().trim();
            if (!StringUtil.checkEmail(email)) {
                ToastUtils.showShort("账号或密码有误");
                return;
            }
            pwd = etPasswd.getText().toString().trim();
            if (pwd == null) {
                ToastUtils.showShort("账号或密码有误");
                return;
            }
            Login(email, pwd);
        });
    }

    private void Login(String email, String pwd) {
        UserServiceFactory.engineerLogin(email, pwd).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody>(this) {
            @Override
            public void onNext(UserResponseBody userResponseBody) {
                DKUserManager.resetUserInfo();
                DKUserManager.saveUserInfo(userResponseBody);
                ARouter.getInstance().build(HomeProtocol.HOMEACTIVITY).navigation();
                SharedPreferencesUtils.saveString(EnterpriseLoginActivity.this, "identity", "enterprise");
                finish();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    ToastUtils.showShort((exception.getMsg()));
                }else {
                    super.onError(e);
                }
            }
        });
    }

    private void initView() {
        or = findViewById(R.id.or);
        rl = findViewById(R.id.rl);
        login = findViewById(R.id.login);
        passwd = findViewById(R.id.passwd);
        btLogin = findViewById(R.id.bt_login);
        register = findViewById(R.id.register);
        etPasswd = findViewById(R.id.et_passwd);
        usersLogin = findViewById(R.id.users_login);
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        etPhoneNum = findViewById(R.id.et_phone_num);
        dividerPhone = findViewById(R.id.divider_phone);
        dividerPasswd = findViewById(R.id.divider_passwd);
        forgetPassword = findViewById(R.id.forget_password);
    }
}
