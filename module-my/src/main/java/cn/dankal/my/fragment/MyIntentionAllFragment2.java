package cn.dankal.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyIntentionRvAdapter;
import cn.dankal.basiclib.base.BaseRvFragmentImp;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.setting.R;

public class MyIntentionAllFragment2 extends BaseRvFragmentImp<MyIntentionBean>{

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initComponents(View view) {

    }

    @Override
    public BaseRecyclerViewPresenter<MyIntentionBean> getPresenter() {
        return new MyIntentPresenter();
    }

    @Override
    public BaseRecyclerViewAdapter<MyIntentionBean> getAdapter() {
        MyIntentionRvAdapter myIntentionRvAdapter = new MyIntentionRvAdapter();
        myIntentionRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyIntentionBean>() {
            @Override
            public void onItemClick(View v, int position, MyIntentionBean data) {
                ARouter.getInstance().build(MyProtocol.MYINTENTIONDETA).navigation();
            }
        });
        return myIntentionRvAdapter;
    }

}
