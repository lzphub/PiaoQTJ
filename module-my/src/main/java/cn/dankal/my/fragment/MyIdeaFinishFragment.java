package cn.dankal.my.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.dankal.basiclib.adapter.MyIdeaListRvAdapter;
import cn.dankal.basiclib.base.fragment.BaseRecyclerViewFragment;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyIdeaListBean;
import cn.dankal.basiclib.common.OnFinishLoadDataListener;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.presenter.MyIdeaListPersenter;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

public class MyIdeaFinishFragment extends BaseRecyclerViewFragment<MyIdeaListBean.DataBean> {


    private MyIdeaListRvAdapter myIdeaListRvAdapter;
    private MyIdeaListPersenter myIdeaListPersenter;

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
    public BaseRecyclerViewPresenter<MyIdeaListBean.DataBean> getPresenter() {
        myIdeaListPersenter = new MyIdeaListPersenter();
        myIdeaListPersenter.requestData("4");
        return myIdeaListPersenter;
    }

    @Override
    public BaseRecyclerViewAdapter<MyIdeaListBean.DataBean> getAdapter() {
        myIdeaListRvAdapter=new MyIdeaListRvAdapter();
        myIdeaListRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyIdeaListBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, MyIdeaListBean.DataBean data) {
                ARouter.getInstance().build(MyProtocol.IDEADATA).withSerializable("data",data).navigation();
            }
        });
        return myIdeaListRvAdapter;
    }

    @Override
    public OnFinishLoadDataListener setOnFinishLoadDataListener() {
        return null;
    }
}
