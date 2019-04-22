package cn.xunzi.my.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.fragment.BaseFragment;
import cn.xunzi.my.entity.TabEntity;
import cn.xunzi.my.fragment.CashInFragment;
import cn.xunzi.my.fragment.WithFinishFragment;
import cn.xunzi.my.fragment.WithReturnFragment;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.WITHDETA;

@Route(path = WITHDETA)
public class WithDetaActivity extends BaseActivity {

    private ImageView ivBack;
    private SlidingTabLayout tabTitle;
    private ViewPager tabViewpager;
    private String[] tab_titel={"提现中","已退回","已成功"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<BaseFragment> fragmentList=new ArrayList<>();
    private CashInFragment cashInFragment;
    private WithReturnFragment withReturnFragment;
    private WithFinishFragment withFinishFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_deta;
    }

    @Override
    protected void initComponents() {
        initView();
        for(int i=0;i<tab_titel.length;i++){
            mTabEntities.add(new TabEntity(tab_titel[i]) {
            });
        }
        ivBack.setOnClickListener(view -> finish());
        initViewPager();
        tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tabViewpager.setCurrentItem(0);
    }
    private void initViewPager(){
        if(null == fragmentList){
            fragmentList = new ArrayList<>();
        }
        if(null==cashInFragment){
            cashInFragment=new CashInFragment();
            fragmentList.add(cashInFragment);
        }
        if(null  == withReturnFragment){
            withReturnFragment = new WithReturnFragment();
            fragmentList.add(withReturnFragment);
        }
        if(null  == withFinishFragment){
            withFinishFragment = new WithFinishFragment();
            fragmentList.add(withFinishFragment);
        }


        tabViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), (ArrayList<BaseFragment>) fragmentList,tab_titel));
        tabTitle.setViewPager(tabViewpager);

        tabViewpager.setOffscreenPageLimit(3);

    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tabTitle = findViewById(R.id.tab_title);
        tabViewpager = findViewById(R.id.tab_viewpager);
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
