package cn.dankal.my.activity;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout ;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYWORKLIST;

@Route(path = MYWORKLIST)
public class MyWorkListActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private CommonTabLayout  tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;
    private String[] tab_titel={"全部","进行中","已完成"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list;
    }

    @Override
    protected void initComponents() {
        initView();
//        tabTitle.setViewPager(tabViewpager,tab_titel);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for(int i=0;i<tab_titel.length;i++){
            mTabEntities.add(new TabEntity(tab_titel[i]) {
            });
        }
        tabTitle.setTabData(mTabEntities);
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tabTitle = (CommonTabLayout ) findViewById(R.id.tab_title);
        tabViewpager = (ViewPager) findViewById(R.id.tab_viewpager);
    }


}
