package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import api.ProductServiceFactory;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.exception.LocalException;
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
    public void search(String keyword, String category_uuid,String tag) {
        ProductServiceFactory.getProductlist(keyword, category_uuid,tag).safeSubscribe(new AbstractDialogSubscriber<ProductHomeListBean>(searchview) {
            @Override
            public void onNext(ProductHomeListBean productListBean) {
                searchview.serarchDataSuccess(productListBean);
            }

            @Override
            public void onError(Throwable e) {
                searchview.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if(exception.getMsg().equals("keyword长度不符合要求 2,32")){
                        ToastUtils.showShort("Enter at least two characters");
                    }
                }
            }
        });
    }

    @Override
    public void demandSearch(String keyWord) {
        ProductServiceFactory.searchDemandList(keyWord).safeSubscribe(new AbstractDialogSubscriber<DemandListbean>(searchview) {
            @Override
            public void onNext(DemandListbean demandListbean) {
                searchview.demandSearchSuccess(demandListbean);
            }

            @Override
            public void onError(Throwable e) {
                searchview.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if(exception.getMsg().equals("keyword长度不符合要求 2,32")){
                        ToastUtils.showShort("至少输入两个字符");
                    }
                }
            }
        });
    }
}
