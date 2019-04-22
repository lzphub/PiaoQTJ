package cn.xunzi.basiclib.base.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewContract;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.xunzi.basiclib.common.OnFinishLoadDataListener;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
import cn.xunzi.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;

import static cn.xunzi.basiclib.Constants.PAGE_SIZE;

/**
 * @author xunzi Android Developer
 */
public abstract class BaseRecyclerViewActivity<M> extends BaseStateActivity implements
        BaseRecyclerViewContract.RecyclerViewView<M> {

    protected BaseRecyclerViewAdapter<M> mAdapter;
    protected BaseRecyclerViewPresenter mPresenter;
    protected SwipeToLoadLayout swipeToLoadLayout;
    protected RecyclerView recyclerView;
    protected OnFinishLoadDataListener onFinishLoadDataListener;

    private List<M> mData = new ArrayList<>();
    private int pageIndex = 1;
    private boolean isUpdateList = false;
    private boolean isRefresh = true;
    private String type;

    @Override
    protected void initComponents() {
        mPresenter = getPresenter();
        type= SharedPreferencesUtils.getString(this, "identity", "user");
        if (mPresenter != null) mPresenter.attachView(this);

        mAdapter = getAdapter();
        recyclerView = getRecyclerView();
        if (mAdapter != null&&recyclerView!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mAdapter);
        }
        onFinishLoadDataListener = setOnFinishLoadDataListener();

        showLoading();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public void obtainData() {
        super.obtainData();
        pageIndex = 1;
        if (mPresenter != null)
            mPresenter.requestData(String.valueOf(pageIndex));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void render(List<M> t) {
        showContent();
        //不能同时结束刷新和加载更多，界面会不和谐
        if(t == null || t.size()<Integer.valueOf(PAGE_SIZE)){
            swipeToLoadLayout.setLoadMoreEnabled(false);
        }
        if (isRefresh) {
            onFinishLoadDataListener.finishRefresh();
            if (t != null && t.size() > 0) {
                if (mAdapter != null) {
                    mData = t;
                    mAdapter.updateData(mData);
                }
            } else {
                if (mAdapter != null)
                    mAdapter.clearData();

            }
        } else {
            onFinishLoadDataListener.finishLoadMore();
            if (t.size() > 0) {
                mData.addAll(t);
                if (mAdapter != null) mAdapter.updateData(t);
            }
        }
        if (mAdapter != null && mAdapter.isEmpty()) {
            initLoadService();
            if("user".equals(type)){
                showEnEmpty();
            }else{
                showEmpty();
            }
        }
    }

    @Override
    public void _onRefresh() {
        pageIndex = 1;
        isRefresh = true;
        if (mPresenter != null) mPresenter.requestData(String.valueOf(pageIndex));
    }

    @Override
    public void _onLoadMore() {
        pageIndex++;
        isRefresh = false;
        if (mPresenter != null) mPresenter.requestData(String.valueOf(pageIndex));
    }

}
