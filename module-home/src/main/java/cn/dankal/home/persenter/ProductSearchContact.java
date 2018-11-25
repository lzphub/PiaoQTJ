package cn.dankal.home.persenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.UserHomeBannerBean;

public interface ProductSearchContact {

    interface searchview extends BaseView{
        void serarchDataSuccess(ProductListBean productListBean);
        void demandSearchSuccess(DemandListbean demandListbean);
    }

    interface productSearchPresenter extends BasePresenter<searchview>{
        void search(String keyword, String category_uuid);
        void demandSearch(String keyWord);
    }

}
