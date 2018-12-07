package cn.dankal.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.my.fragment.MyIdeaAllFragment;
import cn.dankal.my.fragment.MyIdeaFinishFragment;
import cn.dankal.my.fragment.MyIdeaOngoingFragment;
import cn.dankal.my.fragment.MyIdeaPromotionFragment;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYIDEA;

/**
 * 我的创意
 */

@Route(path = MYIDEA)
public class MyIdeaActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private com.flyco.tablayout.SlidingTabLayout tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;
    private List<BaseFragment> fragmentList=new ArrayList<>();
    private MyIdeaAllFragment myIdeaAllFragment;
    private MyIdeaOngoingFragment myIdeaOngoingFragment;
    private MyIdeaFinishFragment myIdeaFinishFragment;
    private MyIdeaPromotionFragment myIdeaPromotionFragment;

    private String[] tab_titel2={"全部","进行中","已完成","产品推广中"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list_right20;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());

        if(null == fragmentList){
            fragmentList = new ArrayList<>();
        }
        if(null==myIdeaAllFragment){
            myIdeaAllFragment=new MyIdeaAllFragment();
            fragmentList.add(myIdeaAllFragment);
        }
        if(null  == myIdeaOngoingFragment){
            myIdeaOngoingFragment = new MyIdeaOngoingFragment();
            fragmentList.add(myIdeaOngoingFragment);
        }
        if(null  == myIdeaFinishFragment){
            myIdeaFinishFragment = new MyIdeaFinishFragment();
            fragmentList.add(myIdeaFinishFragment);
        }
        if(null  == myIdeaPromotionFragment){
            myIdeaPromotionFragment = new MyIdeaPromotionFragment();
            fragmentList.add(myIdeaPromotionFragment);
        }

        tabViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,tab_titel2));
        tabTitle.setViewPager(tabViewpager);
        tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        titleText.setText("我的创意");

        tabViewpager.setOffscreenPageLimit(4);
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
        tabTitle = findViewById(R.id.tab_title);
        tabViewpager = findViewById(R.id.tab_viewpager);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<BaseFragment> mFragments;
        private String[] mTitles;

        public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> mFragments, String[] mTitles) {
            super(fm);
            this.mFragments= (ArrayList<BaseFragment>) mFragments;
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
