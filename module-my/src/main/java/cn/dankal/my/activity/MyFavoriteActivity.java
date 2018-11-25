package cn.dankal.my.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyFavoriteRvAdapter;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.base.recyclerview.OnRvItemLongClickListener;
import cn.dankal.basiclib.base.recyclerview.del.DeleteRecyclerView;
import cn.dankal.basiclib.base.recyclerview.del.OnDelItemClickListener;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnLoadMoreListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.OnRefreshListener;
import cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout;
import cn.dankal.my.presenter.MyFavoriteContact;
import cn.dankal.my.presenter.MyFavoritePresenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYFAVORITE;

@Route(path = MYFAVORITE)
public class MyFavoriteActivity extends BaseStateActivity implements MyFavoriteContact.fcView{

    private ImageView backImg;
    private cn.dankal.basiclib.widget.swipetoloadlayout.SwipeToLoadLayout swipeToloadLayout;
    private cn.dankal.basiclib.base.recyclerview.del.DeleteRecyclerView swipeTarget;
    private MyFavoritePresenter myFavoritePresenter=new MyFavoritePresenter();
    private MyFavoriteRvAdapter myFavoriteRvAdapter=new MyFavoriteRvAdapter();
    private List<ProductListBean.DataBean> dataBeanList=new ArrayList<>();
    private int pageIndex = 1;
    private boolean isUpdateList = false;
    private boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_favorite;
    }

    @Override
    public void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        swipeTarget.setAdapter(myFavoriteRvAdapter);
        myFavoritePresenter.attachView(this);
        myFavoritePresenter.getData(1,10);

        swipeTarget.setDelOnItemClickListener(new OnDelItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid",dataBeanList.get(position).getProduct_uuid()).navigation();
            }

            @Override
            public void onDeleteClick(int position) {
                myFavoritePresenter.delete(dataBeanList.get(position).getProduct_uuid());
            }
        });

        swipeToloadLayout.setOnLoadMoreListener(() -> {
            pageIndex++;
            isRefresh = false;
            myFavoritePresenter.getData(pageIndex, 10);
        });
        swipeToloadLayout.setOnRefreshListener(() -> {
            pageIndex=1;
            isRefresh = true;
            myFavoritePresenter.getData(pageIndex, 10);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myFavoritePresenter.getData(1,10);
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        swipeToloadLayout = findViewById(R.id.swipe_toload_layout);
        swipeTarget = findViewById(R.id.swipe_target);
    }

    @Override
    public Object contentView() {
        return swipeToloadLayout;
    }

    @Override
    public void obtainData() {
        super.obtainData();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void getDataSuccess(ProductListBean productListBean) {
        if(productListBean.getData().size()==0){
            initLoadService();
            showEnEmpty();
        }
        if (dataBeanList != null) {
            dataBeanList.clear();
        }
        if (isRefresh) {
            myFavoriteRvAdapter.clearData();
            swipeToloadLayout.setRefreshing(false);
        } else {
            swipeToloadLayout.setLoadingMore(false);
        }
        dataBeanList.addAll(productListBean.getData());
        myFavoriteRvAdapter.addMore(dataBeanList);
    }

    @Override
    public void getDataFail() {
    }

    @Override
    public void updata(ProductListBean productListBean) {
        if (dataBeanList != null) {
            dataBeanList.clear();
        }
        dataBeanList.addAll(productListBean.getData());
        myFavoriteRvAdapter.updateData(dataBeanList);
    }
}
