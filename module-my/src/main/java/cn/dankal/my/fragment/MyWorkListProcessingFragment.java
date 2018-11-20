package cn.dankal.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.dankal.basiclib.adapter.MyWorkListRvAdapter;
import cn.dankal.basiclib.base.fragment.BaseStateFragment;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.presenter.MyWorkListContact;
import cn.dankal.my.presenter.MyWorklistPresenter;
import cn.dankal.setting.R;

public class MyWorkListProcessingFragment extends BaseStateFragment implements MyWorkListContact.mwView {

    private SwipeToLoadLayout swipeToloadLayout;
    private RecyclerView swipeTarget;
    private MyWorklistPresenter worklistPresenter=MyWorklistPresenter.getWorklistPresenter();
    private int page=1,size=10;
    private MyWorkListRvAdapter myWorkListRvAdapter;

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
        worklistPresenter.attachView(this);
        worklistPresenter.getData(page+"",size+"","processing");
    }

    @Override
    public void getDataSuccess(MyWorkListBean myWorkListBean) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeTarget.setLayoutManager(linearLayoutManager);
        myWorkListRvAdapter=new MyWorkListRvAdapter();
        myWorkListRvAdapter.addMore(myWorkListBean.getData());
        swipeTarget.setAdapter(myWorkListRvAdapter);
    }

    @Override
    public Object contentView() {
        return R.id.swipe_toload_layout;
    }

    private void initView(View view) {
        swipeToloadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipe_toload_layout);
        swipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
    }
}