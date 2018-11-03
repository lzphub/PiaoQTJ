package cn.dankal.home.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.home.fragment.Home_fragment;
import cn.dankal.home.fragment.My_fragment;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMEACTIVITY;

@Route(path = HOMEACTIVITY)
public class HomeActivity extends BaseActivity {

    private android.widget.RadioGroup tabRg;
    private android.widget.RadioButton homeRbtn;
    private android.widget.TextView releaseText;
    private android.widget.RadioButton myRbtn;
    private android.widget.FrameLayout homeFra;
    private FragmentManager manager;
    private FragmentTransaction transaction;

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
        myRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    transaction=manager.beginTransaction();
                    My_fragment my_fragment=new My_fragment();
                    transaction.replace(R.id.home_fra,my_fragment).commit();
                }
            }
        });
        homeRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    transaction=manager.beginTransaction();
                    Home_fragment homeFragment=new Home_fragment();
                    transaction.replace(R.id.home_fra,homeFragment).commit();
                }
            }
        });
        releaseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation();
            }
        });
    }

    private void initView() {
        tabRg = (RadioGroup) findViewById(R.id.tab_rg);
        homeRbtn = (RadioButton) findViewById(R.id.home_rbtn);
        releaseText = (TextView) findViewById(R.id.release_text);
        myRbtn = (RadioButton) findViewById(R.id.my_rbtn);
        homeFra = (FrameLayout) findViewById(R.id.home_fra);
    }
}
