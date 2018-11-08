package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.activity.BaseRecyclerViewActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.common.OnFinishLoadDataListener;
import cn.dankal.basiclib.common.cache.CacheManager;
import cn.dankal.basiclib.common.cache.SDCacheDir;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SETTING;

@Route(path = SETTING)
public class SettingActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout problem;
    private android.widget.RelativeLayout aboutUs;
    private android.widget.RelativeLayout clearCache;
    private android.widget.RelativeLayout opinion;
    private android.widget.RelativeLayout setPwd;
    private android.widget.RelativeLayout loginOut;
    private String type;
    private TextView aboutText;
    private TextView clearText;
    private TextView opinionText;
    private TextView outText;
    private TextView problemText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.ABOUTUS).navigation();
            }
        });
        opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.OPINION).navigation();
            }
        });
        setPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.SETWITHPEDCODE).navigation();
            }
        });
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
                ActivityUtils.finishAllActivities();
            }
        });
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.COMPROB).navigation();
            }
        });
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long l=CacheManager.cleanCache(SettingActivity.this,SDCacheDir.getInstance(SettingActivity.this).cachepath);
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        problem = (RelativeLayout) findViewById(R.id.problem);
        aboutUs = (RelativeLayout) findViewById(R.id.about_us);
        clearCache = (RelativeLayout) findViewById(R.id.clear_cache);
        opinion = (RelativeLayout) findViewById(R.id.opinion);
        setPwd = (RelativeLayout) findViewById(R.id.set_pwd);
        loginOut = (RelativeLayout) findViewById(R.id.login_out);
        problemText = (TextView) findViewById(R.id.problem_text);
        aboutText = (TextView) findViewById(R.id.about_text);
        clearText = (TextView) findViewById(R.id.clear_text);
        opinionText = (TextView) findViewById(R.id.opinion_text);
        outText = (TextView) findViewById(R.id.out_text);
        type= SharedPreferencesUtils.getString(this,"identity","");
        if(type.equals("user")){
            problemText.setText("FAQ");
            aboutText.setText("ABOUT US");
            clearText.setText("CLEAR CACHE");
            opinionText.setText("HELP AND FEEDBACK");
            outText.setText("LOG OUT");
            setPwd.setVisibility(View.GONE);
        }
    }

}
