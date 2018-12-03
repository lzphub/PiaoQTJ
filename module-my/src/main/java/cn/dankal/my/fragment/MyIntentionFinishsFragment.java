package cn.dankal.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dankal.basiclib.adapter.MyIntentionRvAdapter;
import cn.dankal.basiclib.base.BaseRvFragmentImp;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.GetIntentionBean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.presenter.MyIntentContact;
import cn.dankal.my.presenter.MyIntentPresenter;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

public class MyIntentionFinishsFragment extends BaseFragment implements MyIntentContact.fcView {

    @BindView(R2.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R2.id.swipe_toload_layout)
    SwipeToLoadLayout swipeToloadLayout;
    Unbinder unbinder;
    private int pageIndex = 1;
    private MyIntentPresenter myIntentPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview_en;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeTarget.setLayoutManager(linearLayoutManager);
        myIntentPresenter = new MyIntentPresenter();
        myIntentPresenter.attachView(this);
        List<String> statues=new ArrayList<>();
        statues.add("5");
        GetIntentionBean getIntentionBean=new GetIntentionBean();
        getIntentionBean.setPage_size(20);
        getIntentionBean.setPage_index(pageIndex);
        getIntentionBean.setStatus(statues);
        myIntentPresenter.getData(getIntentionBean);
        swipeToloadLayout.setOnLoadMoreListener(() -> {
            pageIndex++;
            myIntentPresenter.addData(getIntentionBean);
        });
        swipeToloadLayout.setOnRefreshListener(() -> {
            pageIndex=1;
            myIntentPresenter.getData(getIntentionBean);
        });
    }

    @Override
    public void getDataSuccess(MyIntentionBean intentionBean) {
        if(intentionBean.getData().size()<20){
            swipeToloadLayout.setLoadMoreEnabled(false);
        }
        MyIntentionRvAdapter myIntentionRvAdapter = new MyIntentionRvAdapter();
        swipeTarget.setAdapter(myIntentionRvAdapter);
        myIntentionRvAdapter.updateData(intentionBean.getData());
        swipeToloadLayout.setRefreshing(false);
        myIntentionRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyIntentionBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, MyIntentionBean.DataBean data) {
                ARouter.getInstance().build(MyProtocol.MYINTENTIONDETA).withString("intention_id",data.getIntention_id()).withString("product_id",data.getProduct_uuid()).navigation();
            }
        });
    }

    @Override
    public void updata(MyIntentionBean intentionBean) {
        if(intentionBean.getData().size()<20){
            swipeToloadLayout.setLoadMoreEnabled(false);
        }
        MyIntentionRvAdapter myIntentionRvAdapter = new MyIntentionRvAdapter();
        swipeTarget.setAdapter(myIntentionRvAdapter);
        myIntentionRvAdapter.addMore(intentionBean.getData());
        swipeToloadLayout.setLoadingMore(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}