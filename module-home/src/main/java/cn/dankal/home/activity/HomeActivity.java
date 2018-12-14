package cn.dankal.home.activity;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


import java.util.HashSet;
import java.util.Set;

import api.MyServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.EventBusBean;
import cn.dankal.basiclib.bean.HasNewBean;
import cn.dankal.basiclib.eventbus.AppBus;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.fragment.Home_fragment;
import cn.dankal.home.fragment.My_fragment;
import cn.jpush.android.api.JPushInterface;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMEACTIVITY;

@Route(path = HOMEACTIVITY)
public class HomeActivity extends BaseActivity {

    private android.widget.RadioButton homeRbtn;
    private android.widget.TextView releaseText;
    private android.widget.RadioButton myRbtn;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private long exitTime = 0;
    private AppBus appBus;

    @Override
    protected int getLayoutId() {
        appBus=AppBus.getInstance();
        return R.layout.activity_home;
    }

    @Override
    protected void initComponents() {
        initView();
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        Home_fragment homeFragment=new Home_fragment();
        transaction.replace(R.id.home_fra,homeFragment).commit();
        myRbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                transaction=manager.beginTransaction();
                My_fragment my_fragment=new My_fragment();
                transaction.replace(R.id.home_fra,my_fragment).commit();
            }
        });
        homeRbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                transaction=manager.beginTransaction();
                Home_fragment homeFragment1 =new Home_fragment();
                transaction.replace(R.id.home_fra, homeFragment1).commit();
            }
        });
        releaseText.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation());
    }

    private void initView() {
        homeRbtn = findViewById(R.id.home_rbtn);
        releaseText = findViewById(R.id.release_text);
        myRbtn = findViewById(R.id.my_rbtn);
        setAlias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        downTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }

    @Override
    public void showLoadingDialog() {

    }

    //十秒获取一次是否有新消息
    CountDownTimer downTimer=new CountDownTimer(1000000,10000) {
        @Override
        public void onTick(long millisUntilFinished) {
            MyServiceFactory.getEngHasNew().safeSubscribe(new AbstractDialogSubscriber<HasNewBean>(HomeActivity.this) {
                @Override
                public void onNext(HasNewBean hasNewBean) {
                    if(hasNewBean.getHas_new()==1){
                        appBus.post(new EventBusBean("1"));
                        SharedPreferencesUtils.saveBoolean(HomeActivity.this,"engineerNewMsg",true);
                    }
                }

                @Override
                public void onError(Throwable e) {
                }
            });
        }

        @Override
        public void onFinish() {
            downTimer.start();
        }
    };

    /**
     * 注册极光推送别名
     */
    private void setAlias(){
        JPushInterface.setAlias(this,10, DKUserManager.getUserInfo().getUuid());
        Set<String> tags = new HashSet<>();
        tags.add("engineer");
        JPushInterface.setTags(this,10,tags);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
