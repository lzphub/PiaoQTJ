package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import api.ProductServiceFactory;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.activity.HomeSearchActivity;

public class ProductSearchPresenter implements ProductSearchContact.productSearchPresenter {

    private ProductSearchContact.searchview searchview;

    private static ProductSearchPresenter productSearchPresenter = null;

    public static synchronized ProductSearchPresenter getPresenter() {
        if (productSearchPresenter == null) {
            productSearchPresenter = new ProductSearchPresenter();
        }
        return productSearchPresenter;
    }

    @Override
    public void attachView(@NonNull ProductSearchContact.searchview view) {
        searchview=view;
    }

    @Override
    public void detachView() {
        searchview=null;
    }

    @Override
    public void search(String keyword, String category_uuid) {
        ProductServiceFactory.getProductlist(keyword, category_uuid).safeSubscribe(new AbstractDialogSubscriber<ProductListBean>(searchview) {
            @Override
            public void onNext(ProductListBean productListBean) {
                searchview.serarchDataSuccess(productListBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(e.getMessage()+"");
            }
        });
    }
}
