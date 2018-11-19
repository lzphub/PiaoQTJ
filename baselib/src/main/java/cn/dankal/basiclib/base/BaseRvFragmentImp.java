package cn.dankal.basiclib.base;


import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.fragment.BaseRecyclerViewFragment;
import cn.dankal.basiclib.common.OnFinishLoadDataListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;

/**
 * @author Dankal Android Developer
 * @since 2018/7/17
 */

public abstract class BaseRvFragmentImp<M> extends BaseRecyclerViewFragment<M> implements
        OnRefreshListener, OnLoadMoreListener,
        OnFinishLoadDataListener {


    @Override
    public void initComponents() {
        super.initComponents();
        swipeToLoadLayout = mContentView.findViewById(R.id.swipe_toload_layout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
}

    @Override
    public void onRefresh() {
        _onRefresh();
    }

    @Override
    public void onLoadMore() {
        _onLoadMore();
    }

    @Override
    public OnFinishLoadDataListener setOnFinishLoadDataListener() {
        return this;
    }

    @Override
    public void finishRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void finishLoadMore() {
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
//        checkLoad();
    }
}
