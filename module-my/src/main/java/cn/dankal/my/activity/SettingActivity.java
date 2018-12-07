package cn.dankal.my.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.DKUserManager;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.common.cache.CacheManager;
import cn.dankal.basiclib.common.cache.SDCacheDir;
import cn.dankal.basiclib.protocol.LoginProtocol;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SETTING;

/**
 * 设置
 */
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
    private TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initComponents() {
        initView();

        backImg.setOnClickListener(v -> finish());
        aboutUs.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.ABOUTUS).navigation());
        opinion.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.OPINION).navigation());
        problem.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.COMPROB).navigation());
        setPwd.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.SETWITHPEDCODE).navigation());

        loginOut.setOnClickListener(v -> {
            DKUserManager.resetToken();
            DKUserManager.resetUserInfo();
            if (type.equals("user")) {
                ARouter.getInstance().build(LoginProtocol.USERSLOGIN).navigation();
            } else {
                ARouter.getInstance().build(LoginProtocol.ENTERPRISELOGIN).navigation();
            }
            ActivityUtils.finishAllActivities();
        });

        clearCache.setOnClickListener(v -> {
            CacheManager.cleanCache(SettingActivity.this, SDCacheDir.getInstance(SettingActivity.this).cachepath);
            CacheManager.cleanCache(SettingActivity.this, SDCacheDir.getInstance(SettingActivity.this).cachepath2);
            CacheManager.cleanCache(SettingActivity.this, SDCacheDir.getInstance(SettingActivity.this).filesDir);
            CacheManager.cleanCache(SettingActivity.this, SDCacheDir.getInstance(SettingActivity.this).filesDir2);
            if(type.equals("user")){
                ToastUtils.showShort("Clean Up");
            }else{
                ToastUtils.showShort("清理完成");
            }
        });
    }

    private void initView() {
        setPwd = findViewById(R.id.set_pwd);
        problem = findViewById(R.id.problem);
        opinion = findViewById(R.id.opinion);
        backImg = findViewById(R.id.back_img);
        aboutUs = findViewById(R.id.about_us);
        outText = findViewById(R.id.out_text);
        tvTitle = findViewById(R.id.tv_title);
        loginOut = findViewById(R.id.login_out);
        aboutText = findViewById(R.id.about_text);
        clearText = findViewById(R.id.clear_text);
        clearCache = findViewById(R.id.clear_cache);
        problemText = findViewById(R.id.problem_text);
        opinionText = findViewById(R.id.opinion_text);

        type = SharedPreferencesUtils.getString(this, "identity", "");

        if (type.equals("user")) {
            problemText.setText("FAQ");
            aboutText.setText("ABOUT US");
            clearText.setText("CLEAR CACHE");
            opinionText.setText("HELP AND FEEDBACK");
            outText.setText("LOG OUT");
            setPwd.setVisibility(View.GONE);
            tvTitle.setText("SETTING");
        }
    }

}
