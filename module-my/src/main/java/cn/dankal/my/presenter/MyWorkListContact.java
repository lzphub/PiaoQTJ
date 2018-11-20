package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface MyWorkListContact {

    interface mwView extends BaseView{
        void getDataSuccess(MyWorkListBean myWorkListBean);
    }

    interface mwPersenter extends BasePresenter<mwView>{
        void getData(String page, String size,String status);
    }

}
