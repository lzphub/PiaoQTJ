package com.example.module_product.presenter;

import java.util.List;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface ProductScreenContact {

    interface psView extends BaseView{
        void getDataSuccess(ProductHomeListBean productListBean);
    }

    interface psPresenter extends BasePresenter<psView>{
        void getData(String keyword,String uuid);
    }
}
