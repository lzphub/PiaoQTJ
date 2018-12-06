package com.example.module_product;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
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
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.Logger;

import static cn.dankal.basiclib.protocol.ProductProtocol.SCREEN;

@Route(path = SCREEN)
public class ScreenActivity extends BaseActivity implements View.OnClickListener, ProductScreenContact.psView {
    private List<String> itemList = new ArrayList<>();
    private ImageView backImg;
    private TextView titleText;
    private ImageView serachImg;
    private Spinner spinner;
    private RecyclerView pageProductRv;
    private List<ProductHomeListBean.DataBean> productListBeanList = new ArrayList<>();
    private ProductScreenPresenter productScreenPresenter = ProductScreenPresenter.getPSPresenter();
    private String uuid;
    private List<String> tag = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initComponents() {
        initView();
        productScreenPresenter.attachView(this);
        uuid = getIntent().getStringExtra("uuid");
        for (int i = 0; i < 5; i++) {
            itemList.add("item" + i);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_un_item, R.id.checked_item);
        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        stringArrayAdapter.addAll(itemList);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tag != null && tag.size() > 0) {
                    productScreenPresenter.upData("", uuid, spinner.getSelectedItem()+"");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        productScreenPresenter.getData("", uuid, "");
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
        serachImg = findViewById(R.id.serach_img);
        spinner = findViewById(R.id.spinner);
        pageProductRv = findViewById(R.id.page_product_rv);

        backImg.setOnClickListener(this);
        serachImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            finish();
        }else if(v.getId()==R.id.serach_img){
            ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation();
        }
    }

    @Override
    public void getDataSuccess(ProductHomeListBean productListBean) {
        if(tag.size()==0){
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_un_item, R.id.checked_item);
            stringArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            stringArrayAdapter.clear();
            stringArrayAdapter.addAll(productListBean.getTag());
            spinner.setAdapter(stringArrayAdapter);
            tag.addAll(productListBean.getTag());
        }
    }

    @Override
    public void upDataSuccess(ProductHomeListBean productHomeListBean) {
        pageProductRv.setLayoutManager(new GridLayoutManager(this, 2));
        ProductScreenRvAdapter productScreenRvAdapter = new ProductScreenRvAdapter();
        productListBeanList=new ArrayList<>();
        productListBeanList.addAll(productHomeListBean.getData());
        productScreenRvAdapter.updateData(productListBeanList);
        pageProductRv.setAdapter(productScreenRvAdapter);
        productScreenRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductHomeListBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, ProductHomeListBean.DataBean data) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid", data.getUuid()).navigation();
            }
        });
    }
}
