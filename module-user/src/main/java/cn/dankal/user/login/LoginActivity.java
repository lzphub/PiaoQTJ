package cn.dankal.user.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.pojo.UserResponseBody;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.user.R;
import cn.jpush.android.api.JPushInterface;

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


        enterpriseLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            finish();
        });
        btLogin.setOnClickListener(v -> {
           email=etPhoneNum.getText().toString().trim();
            pwd=etPasswd.getText().toString().trim();
            if(!StringUtil.checkEmail(email) || pwd==null){
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

          @Override
          public void onError(Throwable e) {
              dismissLoadingDialog();
              if (e instanceof LocalException) {
                  LocalException exception = (LocalException) e;
                  if(exception.getMsg().equals("账号或密码错误")){
                      ToastUtils.showShort("Wrong account or password");
                  }else if(exception.getMsg().equals("网络错误")){
                      ToastUtils.showShort("Network error");
                  }else{
                      super.onError(e);
                  }
              }
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
