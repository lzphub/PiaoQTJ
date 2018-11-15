package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProductDataBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface MyFavoriteContact {

    interface fcView extends BaseView{
        void getDataSuccess(ProductListBean productListBean);
        void updata(ProductListBean productListBean);
    }

    interface fcPersenter extends BasePresenter<fcView>{
        void getData(int page,int size);
        void delete(String uuid);
    }

}
