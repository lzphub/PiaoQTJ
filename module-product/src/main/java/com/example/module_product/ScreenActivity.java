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
import com.example.module_product.presenter.ProductScreenContact;
import com.example.module_product.presenter.ProductScreenPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.dankal.basiclib.adapter.ProductScreenRvAdapter;
import cn.dankal.basiclib.adapter.ProductTabRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;

import static cn.dankal.basiclib.protocol.ProductProtocol.SCREEN;

@Route(path = SCREEN)
public class ScreenActivity extends BaseActivity implements View.OnClickListener,ProductScreenContact.psView {
    private List<String> itemList = new ArrayList<>();
    private ImageView backImg;
    private TextView titleText;
    private ImageView serachImg;
    private Spinner spinner;
    private RecyclerView pageProductRv;
    private List<ProductHomeListBean> productListBeanList=new ArrayList<>();
    private ProductScreenPresenter productScreenPresenter=ProductScreenPresenter.getPSPresenter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initComponents() {
        initView();
        productScreenPresenter.attachView(this);
        backImg.setOnClickListener(this);
        for (int i = 0; i < 5; i++) {
            itemList.add("item" + i);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_un_item, R.id.checked_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        stringArrayAdapter.addAll(itemList);
        spinner.setAdapter(stringArrayAdapter);
        productScreenPresenter.getData("","");
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
        serachImg = findViewById(R.id.serach_img);
        spinner = findViewById(R.id.spinner);
        pageProductRv = findViewById(R.id.page_product_rv);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            finish();
        }
    }

    @Override
    public void getDataSuccess(ProductHomeListBean productListBean) {
        pageProductRv.setLayoutManager(new GridLayoutManager(this,2));
        ProductScreenRvAdapter productScreenRvAdapter=new ProductScreenRvAdapter();
        productListBeanList.add(productListBean);
        productScreenRvAdapter.addMore(productListBeanList);
        pageProductRv.setAdapter(productScreenRvAdapter);
        productScreenRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductHomeListBean>() {
            @Override
            public void onItemClick(View v, int position, ProductHomeListBean data) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid",productListBeanList.get(position).getData().get(0).getUuid()).navigation();
            }
        });
    }
}
