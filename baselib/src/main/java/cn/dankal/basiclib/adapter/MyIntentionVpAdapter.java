package cn.dankal.basiclib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.dankal.basiclib.base.fragment.BaseFragment;

public class MyIntentionVpAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private FragmentManager fragmentManager;

    public MyIntentionVpAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.fragmentManager=fm;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
