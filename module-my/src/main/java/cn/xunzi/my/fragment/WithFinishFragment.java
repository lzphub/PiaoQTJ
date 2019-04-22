package cn.xunzi.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.WithDetaAdapter;
import cn.xunzi.basiclib.base.BaseRvFragmentImp;
import cn.xunzi.basiclib.base.fragment.BaseFragment;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.WithBean;
import cn.xunzi.basiclib.bean.WithDetaBean;
import cn.xunzi.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.xunzi.my.presenter.CashInConract;
import cn.xunzi.my.presenter.CashInPresenter;
import cn.xunzi.setting.R;

public class WithFinishFragment extends BaseFragment implements CashInConract.mtView {

    private WithDetaAdapter withDetaAdapter;
    private SwipeToLoadLayout swipeToloadLayout;
    private RecyclerView swipeTarget;
    private CashInPresenter cashInPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);

        swipeToloadLayout.setLoadMoreEnabled(false);

        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        withDetaAdapter=new WithDetaAdapter();
        swipeTarget.setAdapter(withDetaAdapter);

        cashInPresenter=new CashInPresenter();
        cashInPresenter.attachView(this);
        cashInPresenter.getData(XZUserManager.getUserInfo().getId()+"","1");

        swipeToloadLayout.setOnRefreshListener(() -> cashInPresenter.getData(XZUserManager.getUserInfo().getId() + "", "1"));
    }

    private void initView(View view) {
        swipeToloadLayout = view.findViewById(R.id.swipe_toload_layout);
        swipeTarget = view.findViewById(R.id.swipe_target);
    }

    @Override
    public void getDataSuccess(WithDetaBean withDetaBean) {
        withDetaAdapter.updateData(withDetaBean.getData().getList());
        swipeToloadLayout.setRefreshing(false);
    }

    @Override
    public void getDataFail() {
        swipeToloadLayout.setRefreshing(false);
    }
}
