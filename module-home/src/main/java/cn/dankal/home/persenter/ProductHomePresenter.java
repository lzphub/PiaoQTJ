package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;

public class ProductHomePresenter implements ProductHomeContact.productHomePresenter {

    private ProductHomeContact.phview phview;

    private static ProductHomePresenter productHomePresenter = null;

    public static synchronized ProductHomePresenter getPresenter() {
        if (productHomePresenter == null) {
            productHomePresenter = new ProductHomePresenter();
        }
        return productHomePresenter;
    }

    @Override
    public void getData(int page,int size) {
        HomeServiceFactory.getProduct(page,size).safeSubscribe(new AbstractDialogSubscriber<ProductHomeListBean>(phview) {
            @Override
            public void onNext(ProductHomeListBean productListBean) {
                phview.getDataSuccess(productListBean);
            }

            @Override
            public void onError(Throwable e) {
                phview.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("网络错误")) {
                        ToastUtils.showShort("Network error");
                    }
                }
            }
        });
    }

    @Override
    public void getBanner() {
        HomeServiceFactory.getBanner().safeSubscribe(new AbstractDialogSubscriber<UserHomeBannerBean>(phview) {
            @Override
            public void onNext(UserHomeBannerBean userHomeBannerBean) {
                phview.getBannerSuccess(userHomeBannerBean);
            }

            @Override
            public void onError(Throwable e) {
                phview.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if (exception.getMsg().equals("网络错误")) {
                        ToastUtils.showShort("Network error");
                    }
                }
            }
        });
    }

    @Override
    public void getEngBanner() {
        HomeServiceFactory.getEngBanner().safeSubscribe(new AbstractDialogSubscriber<UserHomeBannerBean>(phview) {
            @Override
            public void onNext(UserHomeBannerBean userHomeBannerBean) {
                phview.getBannerSuccess(userHomeBannerBean);
            }
        });
    }

    @Override
    public void getEngData(int page, int size) {
        HomeServiceFactory.getDemandList(page,size).safeSubscribe(new AbstractDialogSubscriber<DemandListbean>(phview) {
            @Override
            public void onNext(DemandListbean demandListbean) {
                phview.getEngDataSuccess(demandListbean);
            }
        });
    }

    @Override
    public void attachView(@NonNull ProductHomeContact.phview view) {
        phview=view;
    }

    @Override
    public void detachView() {
        phview=null;
    }
}
