package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import api.ProductServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyFavoritePresenter implements MyFavoriteContact.fcPersenter {

    private MyFavoriteContact.fcView fcView;

    @Override
    public void getData(int page,int size) {
        MyServiceFactory.getFavouriteList(page,size).safeSubscribe(new AbstractDialogSubscriber<ProductListBean>(fcView) {
            @Override
            public void onNext(ProductListBean productListBean) {
                fcView.getDataSuccess(productListBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                fcView.getDataFail();
            }
        });
    }

    @Override
    public void delete(String uuid) {
        ProductServiceFactory.deleteColl(uuid).safeSubscribe(new AbstractDialogSubscriber<String>(fcView) {
            @Override
            public void onNext(String s) {
                MyServiceFactory.getFavouriteList(1,10).safeSubscribe(new AbstractDialogSubscriber<ProductListBean>(fcView) {
                    @Override
                    public void onNext(ProductListBean productListBean) {
                        fcView.updata(productListBean);
                    }
                });
            }
        });
    }

    @Override
    public void attachView(@NonNull MyFavoriteContact.fcView view) {
        fcView=view;
    }

    @Override
    public void detachView() {
        fcView=null;
    }
}
