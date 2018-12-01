package cn.dankal.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dankal.basiclib.adapter.MyWorkListRvAdapter;
import cn.dankal.basiclib.base.BaseRvFragmentImp;
import cn.dankal.basiclib.base.fragment.BaseLazyLoadFragment;
import cn.dankal.basiclib.base.fragment.BaseStateFragment;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.presenter.MyWorkListContact;
import cn.dankal.my.presenter.MyWorklistPresenter;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

public class MyWorkListAllFragment extends BaseRvFragmentImp<MyWorkListBean.DataBean> {

    private MyWorklistPresenter worklistPresenter;
    private MyWorkListRvAdapter myWorkListRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initComponents(View view) {

    }

    @Override
    public Object contentView() {
        return R.id.swipe_toload_layout;
    }

    @Override
    public BaseRecyclerViewPresenter<MyWorkListBean.DataBean> getPresenter() {
        worklistPresenter = new MyWorklistPresenter();
        worklistPresenter.setStatus("all");
        return worklistPresenter;
    }

    @Override
    public BaseRecyclerViewAdapter<MyWorkListBean.DataBean> getAdapter() {
        myWorkListRvAdapter = new MyWorkListRvAdapter();
        myWorkListRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyWorkListBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, MyWorkListBean.DataBean data) {
                ARouter.getInstance().build(MyProtocol.WORKDATA).withString("uuid",data.getUuid()).withInt("statusId",data.getStatus()).navigation();
            }
        });
        return myWorkListRvAdapter;
    }
}