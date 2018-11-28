package cn.dankal.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyIntentionVpAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.my.fragment.MyIntentionAllFragment;
import cn.dankal.my.fragment.MyIntentionFinishsFragment;
import cn.dankal.my.fragment.MyIntentionProgressFragment;
import cn.dankal.my.fragment.MyIntentionSubmittedFragment;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYINTENTION;

@Route(path = MYINTENTION)
public class MyIntentionActivity extends BaseActivity {

    private ImageView backImg;
    private TextView titleText;
    private com.flyco.tablayout.SlidingTabLayout tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;
    private String[] tab_titel2={"ALL","SUBMITTED","IN PROGRESS","FINISH"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<BaseFragment> fragmentList;
    private MyIntentionVpAdapter myIntentionVpAdapter;
    private MyIntentionAllFragment myIntentionAllFragment;
    private MyIntentionSubmittedFragment myIntentionSubmittedFragment;
    private MyIntentionProgressFragment myIntentionProgressFragment;
    private MyIntentionFinishsFragment myIntentionFinishsFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list;
    }

    @Override
    protected void initComponents() {
        initView();

        backImg.setOnClickListener(v -> finish());
        titleText.setText("MY INTENTION");
        for(int i=0;i<tab_titel2.length;i++){
            mTabEntities.add(new TabEntity(tab_titel2[i]) {
            });
        }
        initVp();
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
        backImg = findViewById(R.id.back_img);
        titleText =  findViewById(R.id.title_text);
        tabTitle = findViewById(R.id.tab_title);
        tabViewpager =  findViewById(R.id.tab_viewpager);
    }

    private void initVp(){
        if(null == fragmentList){
            fragmentList = new ArrayList<BaseFragment>();
        }

        if(null  == myIntentionAllFragment){
            myIntentionAllFragment = new MyIntentionAllFragment();
            fragmentList.add(myIntentionAllFragment);
        }

        if(null  == myIntentionSubmittedFragment){
            myIntentionSubmittedFragment = new MyIntentionSubmittedFragment();
            fragmentList.add(myIntentionSubmittedFragment);
        }

        if(null  == myIntentionProgressFragment){
            myIntentionProgressFragment = new MyIntentionProgressFragment();
            fragmentList.add(myIntentionProgressFragment);
        }

        if(null  == myIntentionFinishsFragment){
            myIntentionFinishsFragment = new MyIntentionFinishsFragment();
            fragmentList.add(myIntentionFinishsFragment);
        }

        tabViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), (ArrayList<BaseFragment>) fragmentList,tab_titel2));
        tabTitle.setViewPager(tabViewpager);

        tabViewpager.setOffscreenPageLimit(4);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<BaseFragment> mFragments;
        private String[] mTitles;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> mFragments, String[] mTitles) {
            super(fm);
            this.mFragments=mFragments;
            this.mTitles=mTitles;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }
}
