package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class ProductClassifyPresenter implements ProductClassifyContact.productPresenter {

    private ProductClassifyContact.pcview pcview;

    private static ProductClassifyPresenter productClassifyPresenter = null;

    public static synchronized ProductClassifyPresenter getPresenter() {
        if (productClassifyPresenter == null) {
            productClassifyPresenter = new ProductClassifyPresenter();
        }
        return productClassifyPresenter;
    }

    @Override
    public void getData() {
        HomeServiceFactory.getCatTree().safeSubscribe(new AbstractDialogSubscriber<ProductClassifyBean>(pcview) {
            @Override
            public void onNext(ProductClassifyBean productClassifyBean) {
                pcview.getDataSuccess(productClassifyBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull ProductClassifyContact.pcview view) {
        pcview = view;
    }

    @Override
    public void detachView() {
        pcview = null;
    }
}
