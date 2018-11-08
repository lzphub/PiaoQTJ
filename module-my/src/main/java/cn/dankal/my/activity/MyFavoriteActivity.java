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
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.base.recyclerview.OnRvItemLongClickListener;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYFAVORITE;

@Route(path = MYFAVORITE)
public class MyFavoriteActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.support.v7.widget.RecyclerView favoriteList;
    private List<ProductListBean> productListBeanList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_favorite;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        initRv();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        favoriteList = (RecyclerView) findViewById(R.id.favorite_list);
    }

    private void initRv(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoriteList.setLayoutManager(linearLayoutManager);
        for(int i=0;i<10;i++){
            ProductListBean productListBean=new ProductListBean();
            productListBeanList.add(productListBean);
        }
        ProductRvAdapter productRvAdapter=new ProductRvAdapter();
        productRvAdapter.addMore(productListBeanList);
        favoriteList.setAdapter(productRvAdapter);
//        productRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductListBean>() {
//            @Override
//            public void onItemClick(View v, int position, ProductListBean data) {
//                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).navigation();
//            }
//        });
        productRvAdapter.setOnRvLongItemClickListener(new OnRvItemLongClickListener<ProductListBean>() {
            @Override
            public boolean onItemLongClick(View v, int position, ProductListBean data) {
                ToastUtils.showShort(position+"");
                return true;
            }
        });
        favoriteList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!favoriteList.canScrollVertically(1)){
                    productRvAdapter.addMore(productListBeanList);
                }
            }
        });

    }
}
