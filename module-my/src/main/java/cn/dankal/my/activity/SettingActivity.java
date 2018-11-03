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
import cn.dankal.basiclib.protocol.MyProtocol;
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
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        problem = (RelativeLayout) findViewById(R.id.problem);
        aboutUs = (RelativeLayout) findViewById(R.id.about_us);
        clearCache = (RelativeLayout) findViewById(R.id.clear_cache);
        opinion = (RelativeLayout) findViewById(R.id.opinion);
        setPwd = (RelativeLayout) findViewById(R.id.set_pwd);
        loginOut = (RelativeLayout) findViewById(R.id.login_out);
    }

}
