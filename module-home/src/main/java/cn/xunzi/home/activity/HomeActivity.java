package cn.xunzi.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.HomeServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.GetVersionBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.widget.GenDialog;
import cn.xunzi.home.fragment.HomeFragment;
import cn.xunzi.home.fragment.RenwuFragment;
import cn.xunzi.home.fragment.WodeFragment;

import static cn.xunzi.basiclib.protocol.HomeProtocol.HOME;

@Route(path = HOME)
public class HomeActivity extends BaseActivity {
    private RadioGroup tabRg;
    private RadioButton homeRbtn;
    private RadioButton renwuRbtn;
    private RadioButton myRbtn;
    private FrameLayout homeFra;

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
        getVersion();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.home_fra, homeFragment).commit();
        myRbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                transaction = manager.beginTransaction();
                WodeFragment my_fragment = new WodeFragment();
                transaction.replace(R.id.home_fra, my_fragment).commit();
            }
        });

        renwuRbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                transaction = manager.beginTransaction();
                RenwuFragment renwuFragment = new RenwuFragment();
                transaction.replace(R.id.home_fra, renwuFragment).commit();
            }
        });

        homeRbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                transaction = manager.beginTransaction();
                HomeFragment homeFragment1 = new HomeFragment();
                transaction.replace(R.id.home_fra, homeFragment1).commit();
            }
        });
    }

    private void getVersion() {
        HomeServiceFactory.queryVersion("1").safeSubscribe(new AbstractDialogSubscriber<GetVersionBean>(this) {
            @Override
            public void onNext(GetVersionBean getVersionBean) {
                if (getVersionBean.getCode().equals("200")) {
                    try {
                        String verName = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                        if (!("v" + verName).equals(getVersionBean.getData().getVersion().getVersionCode())) {
                            GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(HomeActivity.this);
                            Dialog dialog1;
                            customBuilder2.setContent(R.layout.version_dialog);
                            dialog1 = customBuilder2.create();
                            TextView tv_title = dialog1.findViewById(R.id.tv_title);
                            TextView tv_tip = dialog1.findViewById(R.id.tv_tip);
                            Button bt_up = dialog1.findViewById(R.id.bt_up);
                            dialog1.setCancelable(false);
                            tv_title.setText("版本更新（" + getVersionBean.getData().getVersion().getVersionCode() + ")");
                            tv_tip.setText("更新内容:\n" + getVersionBean.getData().getVersion().getRemark());
                            bt_up.setOnClickListener(v -> {
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(getVersionBean.getData().getVersion().getVersionUrl()));//Url 就是你要打开的网址
                                intent.setAction(Intent.ACTION_VIEW);
                                startActivity(intent); //启动浏览器
                            });
                            dialog1.show();
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void initView() {
        tabRg = findViewById(R.id.tab_rg);
        homeRbtn = findViewById(R.id.home_rbtn);
        renwuRbtn = findViewById(R.id.renwu_rbtn);
        myRbtn = findViewById(R.id.my_rbtn);
        homeFra = findViewById(R.id.home_fra);
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
