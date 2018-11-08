package com.example.module_product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.dankal.basiclib.adapter.ProductScreenRvAdapter;
import cn.dankal.basiclib.adapter.ProductTabRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.ProductProtocol;

import static cn.dankal.basiclib.protocol.ProductProtocol.SCREEN;

@Route(path = SCREEN)
public class ScreenActivity extends BaseActivity implements View.OnClickListener {
    private List<String> itemList = new ArrayList<>();
    private ImageView backImg;
    private TextView titleText;
    private ImageView serachImg;
    private Spinner spinner;
    private RecyclerView pageProductRv;
    private List<ProductListBean> productListBeanList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initComponents() {
        initView();
        initRv();
        backImg.setOnClickListener(this);
        for (int i = 0; i < 5; i++) {
            itemList.add("item" + i);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_un_item, R.id.checked_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        stringArrayAdapter.addAll(itemList);
        spinner.setAdapter(stringArrayAdapter);
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        serachImg = (ImageView) findViewById(R.id.serach_img);
        spinner = (Spinner) findViewById(R.id.spinner);
        pageProductRv = (RecyclerView) findViewById(R.id.page_product_rv);
    }

    private void initRv(){
        pageProductRv.setLayoutManager(new GridLayoutManager(this,2));
        for(int i=0;i<10;i++){
            ProductListBean productListBean=new ProductListBean();
            productListBeanList.add(productListBean);
        }
        ProductScreenRvAdapter productScreenRvAdapter=new ProductScreenRvAdapter();
        productScreenRvAdapter.addMore(productListBeanList);
        pageProductRv.setAdapter(productScreenRvAdapter);
        pageProductRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!pageProductRv.canScrollVertically(1)){
                    productScreenRvAdapter.addMore(productListBeanList);
                }
            }
        });
        productScreenRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductListBean>() {
            @Override
            public void onItemClick(View v, int position, ProductListBean data) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).navigation();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            finish();
        }
    }
}
