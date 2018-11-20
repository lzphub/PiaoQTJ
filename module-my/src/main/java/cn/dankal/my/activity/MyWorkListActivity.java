package cn.dankal.my.activity;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
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
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyIntentionVpAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.my.fragment.MyWorkListAllFragment;
import cn.dankal.my.fragment.MyWorkListProcessingFragment;
import cn.dankal.my.fragment.MyWorkListcompletedFragment;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYWORKLIST;

@Route(path = MYWORKLIST)
public class MyWorkListActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private CommonTabLayout  tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;
    private String[] tab_titel={"全部","进行中","已完成"};
    private MyIntentionVpAdapter myIntentionVpAdapter;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private MyWorkListAllFragment myWorkListAllFragment;
    private MyWorkListProcessingFragment myWorkListProcessingFragment;
    private MyWorkListcompletedFragment myWorkListcompletedFragment;
    private List<BaseFragment> fragmentList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list;
    }

    @Override
    protected void initComponents() {
        initView();
        initViewPager();
//        tabTitle.setViewPager(tabViewpager,tab_titel);
        backImg.setOnClickListener(v -> finish());
        for(int i=0;i<tab_titel.length;i++){
            mTabEntities.add(new TabEntity(tab_titel[i]) {
            });
        }
        tabTitle.setTabData(mTabEntities);

        tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tabViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabTitle.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        tabTitle = (CommonTabLayout ) findViewById(R.id.tab_title);
        tabViewpager = (ViewPager) findViewById(R.id.tab_viewpager);
    }


    private void initViewPager(){
        if(null == fragmentList){
            fragmentList = new ArrayList<BaseFragment>();
        }
        if(null==myWorkListAllFragment){
            myWorkListAllFragment=new MyWorkListAllFragment();
            fragmentList.add(myWorkListAllFragment);
        }
        if(null  == myWorkListProcessingFragment){
            myWorkListProcessingFragment = new MyWorkListProcessingFragment();
            fragmentList.add(myWorkListProcessingFragment);
        }
        if(null  == myWorkListcompletedFragment){
            myWorkListcompletedFragment = new MyWorkListcompletedFragment();
            fragmentList.add(myWorkListcompletedFragment);
        }

        myIntentionVpAdapter = new MyIntentionVpAdapter(getSupportFragmentManager(),fragmentList);
        tabViewpager.setAdapter(myIntentionVpAdapter);


    }

}
