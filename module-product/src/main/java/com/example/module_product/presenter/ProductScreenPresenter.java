package com.example.module_product.presenter;

import android.support.annotation.NonNull;

import api.ProductServiceFactory;
import cn.dankal.basiclib.api.ProductService;
import cn.dankal.basiclib.base.BaseResult;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class ProductScreenPresenter implements ProductScreenContact.psPresenter {

    private ProductScreenContact.psView psView;
    private static ProductScreenPresenter productScreenPresenter = null;

    public static synchronized ProductScreenPresenter getPSPresenter() {
        if (productScreenPresenter == null) {
            productScreenPresenter = new ProductScreenPresenter();
        }
        return productScreenPresenter;
    }

    @Override
    public void getData(String keyword,String uuid,String tag) {
        ProductServiceFactory.getProductlist(keyword,uuid,tag).safeSubscribe(new AbstractDialogSubscriber<ProductHomeListBean>(psView) {
            @Override
            public void onNext(ProductHomeListBean productListBean) {
                psView.getDataSuccess(productListBean);
            }
        });
    }

    @Override
    public void upData(String keyword, String uuid, String tag) {
        ProductServiceFactory.getProductlist(keyword,uuid,tag).safeSubscribe(new AbstractDialogSubscriber<ProductHomeListBean>(psView) {
            @Override
            public void onNext(ProductHomeListBean productListBean) {
                psView.upDataSuccess(productListBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull ProductScreenContact.psView view) {
        this.psView = view;
    }

    @Override
    public void detachView() {
        psView = null;
    }
}
