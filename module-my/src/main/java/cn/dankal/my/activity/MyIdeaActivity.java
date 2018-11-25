package cn.dankal.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.my.fragment.MyIdeaAllFragment;
import cn.dankal.my.fragment.MyRequestFragment;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYIDEA;

@Route(path = MYIDEA)
public class MyIdeaActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private com.flyco.tablayout.SlidingTabLayout tabTitle;
    private android.support.v4.view.ViewPager tabViewpager;
    private List<BaseFragment> fragmentList=new ArrayList<>();

    private String[] tab_titel2={"全部","进行中","已完成","产品推广中"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list_right20;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());

        for(int i=0;i<tab_titel2.length;i++){
            fragmentList.add(new MyIdeaAllFragment());
        }
        tabViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,tab_titel2));
        tabTitle.setViewPager(tabViewpager);
        tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        titleText.setText("我的创意");

        tabViewpager.setOffscreenPageLimit(4);
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        tabTitle = (SlidingTabLayout) findViewById(R.id.tab_title);
        tabViewpager = (ViewPager) findViewById(R.id.tab_viewpager);
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
