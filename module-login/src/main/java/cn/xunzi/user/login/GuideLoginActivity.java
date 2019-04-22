package cn.xunzi.user.login;

import android.os.CountDownTimer;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.UserServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.pojo.UserResponseBody;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.protocol.LoginProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.Logger;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
import cn.xunzi.basiclib.util.TitleBarUtils;
import cn.xunzi.basiclib.widget.statubar.QMUIStatusBarHelper;
import cn.xunzi.user.R;

import static cn.xunzi.basiclib.protocol.LoginProtocol.GUIDELOGIN;

@Route(path = GUIDELOGIN)
public class GuideLoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        downTimer2.start();
        return R.layout.activity_guide_login;
    }


    CountDownTimer downTimer2 = new CountDownTimer(3000, 3000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (XZUserManager.isLogined()) {
                ARouter.getInstance().build(HomeProtocol.HOME).navigation();
            } else {
                ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
            }
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer2.cancel();
    }

    @Override
    protected void initComponents() {

    }


}
