package cn.dankal.my.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.adapter.MyIntentionRvAdapter;
import cn.dankal.basiclib.base.BaseRvFragmentImp;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.my.presenter.MyIntentPresenter;
import cn.dankal.setting.R;

public class MyIntentionAllFragment extends BaseRvFragmentImp<MyIntentionBean.DataBean> {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initComponents(View view) {
    }

    @Override
    public BaseRecyclerViewPresenter<MyIntentionBean.DataBean> getPresenter() {
        MyIntentPresenter myIntentPresenter=new MyIntentPresenter();
        myIntentPresenter.requestData("");
        return myIntentPresenter;
    }

    @Override
    public BaseRecyclerViewAdapter<MyIntentionBean.DataBean> getAdapter() {
        MyIntentionRvAdapter myIntentionRvAdapter = new MyIntentionRvAdapter();
        myIntentionRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyIntentionBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, MyIntentionBean.DataBean data) {
                ARouter.getInstance().build(MyProtocol.MYINTENTIONDETA).withString("intention_id",data.getIntention_id()).navigation();
            }
        });
        return myIntentionRvAdapter;
    }

}