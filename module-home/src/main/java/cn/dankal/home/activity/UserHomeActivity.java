package cn.dankal.home.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dankal.address.R;
import cn.dankal.address.R2;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.home.fragment.Home_fragment;
import cn.dankal.home.fragment.My_fragment;
import cn.dankal.home.fragment.Product_fragment;

import static cn.dankal.basiclib.protocol.HomeProtocol.USERHOME;
/*
    用户端首页
 */

@Route(path = USERHOME)
public class UserHomeActivity extends BaseActivity {

    @BindView(R2.id.home_rbtn)
    RadioButton homeRbtn;
    @BindView(R2.id.product_rbtn)
    TextView productRbtn;
    @BindView(R2.id.release_text)
    TextView releaseText;
    @BindView(R2.id.my_rbtn)
    RadioButton myRbtn;
    @BindView(R2.id.tab_rg)
    RadioGroup tabRg;
    @BindView(R2.id.home_fra)
    FrameLayout homeFra;


    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    protected void initComponents() {
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        Home_fragment homeFragment=new Home_fragment();
        transaction.replace(R.id.home_fra,homeFragment).commit();
        homeRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    transaction=manager.beginTransaction();
                    Home_fragment homeFragment=new Home_fragment();
                    transaction.replace(R.id.home_fra,homeFragment).commit();
                }
            }
        });
        myRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    transaction=manager.beginTransaction();
                    My_fragment my_fragment=new My_fragment();
                    transaction.replace(R.id.home_fra,my_fragment).commit();
                }
            }
        });
        productRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=manager.beginTransaction();
                Product_fragment product_fragment=new Product_fragment();
                transaction.replace(R.id.home_fra,product_fragment).commit();
            }
        });
        releaseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick( R2.id.release_text)
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.release_text) {
        }
    }
}
