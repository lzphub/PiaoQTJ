package cn.dankal.user.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;

import api.UserServiceFactory;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.pojo.UserResponseBody;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.USERSLOGIN;

@Route(path = USERSLOGIN)
public class LoginActivity extends BaseActivity {

    private EditText etPhoneNum;
    private EditText etPasswd;
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
        //自动登录
        if("user".equals(SharedPreferencesUtils.getString(this, "identity", "user"))){
            Logger.d("tokento",DKUserManager.getUserToken().getAccessToken());
            if(DKUserManager.isLogined()){
                refreshToken();
                ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
                SharedPreferencesUtils.saveString(LoginActivity.this, "identity", "user");
                finish();
            }
        }else{
            Logger.d("tokento",DKUserManager.getUserToken().getAccessToken());
            if(DKUserManager.isLogined()){
//                engRefreshToken();
                ARouter.getInstance().build(HomeProtocol.HOMEACTIVITY).navigation();
                SharedPreferencesUtils.saveString(LoginActivity.this, "identity", "enterprise");
                finish();
            }
        }

        enterpriseLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            finish();
        });
        btLogin.setOnClickListener(v -> {
           email=etPhoneNum.getText().toString().trim();
           if(!StringUtil.checkEmail(email)){
               ToastUtils.showShort("Wrong account number or password");
               return;
           }
           pwd=etPasswd.getText().toString().trim();
           if(pwd==null){
               ToastUtils.showShort("Wrong account number or password");
               return;
           }
           userLogin(email,pwd);
        });
        register.setOnClickListener(v -> ARouter.getInstance().build(LoginProtocol.REGISTERUSER).withString("type","sign_up").navigation());
        forgetPassword.setOnClickListener(v -> ARouter.getInstance().build(LoginProtocol.REGISTERUSER).withString("type","change_pwd").navigation());
    }

    /**
     * 登录
     * @param email
     * @param pwd
     */
    private void userLogin(String email, String pwd) {

      UserServiceFactory.login(email,pwd).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody>(this) {
          @Override
          public void onNext(UserResponseBody userResponseBody) {
              DKUserManager.resetUserInfo();
              DKUserManager.saveUserInfo(userResponseBody);
              ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
              SharedPreferencesUtils.saveString(LoginActivity.this, "identity", "user");
              finish();
          }
      });
    }

    /**
     * 刷新token
     */
    private void refreshToken(){
        UserServiceFactory.refreshtoken(DKUserManager.getUserToken().getRefreshToken()).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody.TokenBean>(this) {
            @Override
            public void onNext(UserResponseBody.TokenBean tokenBean) {
                DKUserManager.updateUserToken(tokenBean);
            }
        });
    }
    private void engRefreshToken(){
        UserServiceFactory.engineerRefreshtoken(DKUserManager.getUserToken().getRefreshToken()).safeSubscribe(new AbstractDialogSubscriber<UserResponseBody.TokenBean>(this) {
            @Override
            public void onNext(UserResponseBody.TokenBean tokenBean) {
                DKUserManager.updateUserToken(tokenBean);
            }
        });
    }

    private void initView() {
        etPhoneNum = findViewById(R.id.et_phone_num);
        etPasswd = findViewById(R.id.et_passwd);
        btLogin = findViewById(R.id.bt_login);
        enterpriseLogin = findViewById(R.id.enterprise_login);
        or = findViewById(R.id.or);
        register = findViewById(R.id.register);
        forgetPassword = findViewById(R.id.forget_password);
    }
}
