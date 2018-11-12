package cn.dankal.my.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.base.recyclerview.OnRvItemLongClickListener;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.my.presenter.MyFavoritePresenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYFAVORITE;

@Route(path = MYFAVORITE)
public class MyFavoriteActivity extends BaseRvActivity<ProductListBean> {

    private ImageView backImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_favorite;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        backImg.setOnClickListener(v -> finish());
    }

    @Override
    public BaseRecyclerViewPresenter<ProductListBean> getPresenter() {
        return new MyFavoritePresenter();
    }

    @Override
    public BaseRecyclerViewAdapter<ProductListBean> getAdapter() {
        return new ProductRvAdapter();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
    }
}
