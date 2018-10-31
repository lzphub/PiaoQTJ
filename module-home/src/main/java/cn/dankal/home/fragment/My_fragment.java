package cn.dankal.home.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.fragment.BaseFragment;

public class My_fragment extends BaseFragment {
    private android.widget.ImageView myNews;
    private android.widget.ImageView headPic;
    private android.widget.TextView myPostion;
    private android.widget.TextView myName;

    @Override
    protected int getLayoutId() {
        return R.layout.my_layout;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
    }

    private void initView(View view) {
        myNews = (ImageView) view.findViewById(R.id.my_news);
        headPic = (ImageView) view.findViewById(R.id.head_pic);
        myPostion = (TextView) view.findViewById(R.id.my_postion);
        myName = (TextView) view.findViewById(R.id.my_name);
    }
}
