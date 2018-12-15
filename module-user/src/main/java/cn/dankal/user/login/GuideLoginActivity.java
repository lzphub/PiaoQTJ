package cn.dankal.user.login;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.pojo.UserResponseBody;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.TitleBarUtils;
import cn.dankal.basiclib.widget.statubar.QMUIStatusBarHelper;
import cn.dankal.user.R;

import static cn.dankal.basiclib.protocol.LoginProtocol.GUIDELOGIN;

@Route(path = GUIDELOGIN)
public class GuideLoginActivity extends BaseActivity {

    private Button btUserLogin;
    private Button btEngineerLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_login;
    }

    @Override
    protected void initStatusBar() {
        TitleBarUtils.compat(this, getResources().getColor(cn.dankal.basiclib.R.color.colorFF));
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
    }

    @Override
    protected void initComponents() {
        initView();
        btUserLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
            finish();
        });

        btEngineerLogin.setOnClickListener(v -> {
            ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            finish();
        });

        //自动登录
        if("user".equals(SharedPreferencesUtils.getString(this, "identity", "user"))){
            if(DKUserManager.isLogined()){
                refreshToken();
                ARouter.getInstance().build(HomeProtocol.USERHOME).navigation();
                SharedPreferencesUtils.saveString(GuideLoginActivity.this, "identity", "user");
                finish();
            }
        }else{
            if(DKUserManager.isLogined()){
//                engRefreshToken();
                Logger.d("tokento",DKUserManager.getUserToken().getAccessToken());
                ARouter.getInstance().build(HomeProtocol.HOMEACTIVITY).navigation();
                SharedPreferencesUtils.saveString(GuideLoginActivity.this, "identity", "enterprise");
                finish();
            }
        }
    }


    private void initView() {
        btUserLogin = findViewById(R.id.bt_UserLogin);
        btEngineerLogin = findViewById(R.id.bt_EngineerLogin);
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
}
