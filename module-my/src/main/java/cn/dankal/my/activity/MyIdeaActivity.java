package cn.dankal.my.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYIDEA;

@Route(path = MYIDEA)
public class MyIdeaActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private com.flyco.tablayout.CommonTabLayout tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;

    private String[] tab_titel2={"全部","进行中","已完成","产品推广中"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list;
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
        titleText.setText("我的创意");
        for(int i=0;i<tab_titel2.length;i++){
            mTabEntities.add(new TabEntity(tab_titel2[i]) {
            });
        }
        tabTitle.setTabData(mTabEntities);

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tabTitle = (CommonTabLayout) findViewById(R.id.tab_title);
        tabViewpager = (ViewPager) findViewById(R.id.tab_viewpager);
    }
}
