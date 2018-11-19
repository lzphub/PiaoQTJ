package cn.dankal.home.persenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;

public interface ProductHomeContact {

    interface phview extends BaseView{
        void getDataSuccess(ProductListBean productListBean);
        void getBannerSuccess(UserHomeBannerBean userHomeBannerBean);
    }

    interface productHomePresenter extends BasePresenter<phview>{
        void getData(int page,int size);
        void getBanner();
    }

}
