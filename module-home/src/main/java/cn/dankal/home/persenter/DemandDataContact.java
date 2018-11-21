package cn.dankal.home.persenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductClassifyBean;

public interface DemandDataContact {

    interface pcview extends BaseView{
        void getDataSuccess(ProductClassifyBean productClassifyBean);
    }

    interface productPresenter extends BasePresenter<pcview>{
        void getData();
    }

}
