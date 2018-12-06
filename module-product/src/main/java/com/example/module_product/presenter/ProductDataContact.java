package com.example.module_product.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface ProductDataContact {

    interface pdView extends BaseView{
        void getDataSuccess(ProductDataBean productDataBean);
        void getDataFail();
    }

    interface pdPresenter extends BasePresenter<pdView>{
        void getData(String uuid);
        void addCollection(String uuid);
        void deleteCollection(String uuid);
    }
}
