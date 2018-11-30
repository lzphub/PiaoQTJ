package cn.dankal.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashSet;
import java.util.Set;

import cn.dankal.address.R;
import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.Logger;
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
    private android.widget.FrameLayout homeFra;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private long exitTime = 0;

    @Override
    protected int getLayoutId() {
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
        homeFra = findViewById(R.id.home_fra);
        setAlias();
    }

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
