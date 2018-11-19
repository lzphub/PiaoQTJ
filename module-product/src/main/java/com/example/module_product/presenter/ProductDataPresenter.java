package com.example.module_product.presenter;

import android.support.annotation.NonNull;

import api.ProductServiceFactory;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;

public class ProductDataPresenter implements ProductDataContact.pdPresenter {
    private ProductDataContact.pdView pdView;
    private static ProductDataPresenter productDataPresenter = null;

    public static synchronized ProductDataPresenter getPSPresenter() {
        if (productDataPresenter == null) {
            productDataPresenter = new ProductDataPresenter();
        }
        return productDataPresenter;
    }

    @Override
    public void getData(String uuid) {
        ProductServiceFactory.getProductData(uuid).safeSubscribe(new AbstractDialogSubscriber<ProductDataBean>(pdView) {
            @Override
            public void onNext(ProductDataBean productDataBean) {
                pdView.getDataSuccess(productDataBean);
            }
        });
    }

    @Override
    public void addCollection(String uuid) {
        ProductServiceFactory.addcollection(uuid).safeSubscribe(new AbstractDialogSubscriber<String>(pdView) {
            @Override
            public void onNext(String s) {
                ToastUtils.showShort("Collection of success");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void deleteCollection(String uuid) {
        ProductServiceFactory.deleteColl(uuid).safeSubscribe(new AbstractDialogSubscriber<String>(pdView) {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void attachView(@NonNull ProductDataContact.pdView view) {
        pdView = view;
    }

    @Override
    public void detachView() {
        pdView = null;
    }
}
