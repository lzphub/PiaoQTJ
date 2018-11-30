package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface MyIntentContact {

    interface fcView extends BaseView{
        void getDataSuccess(MyIntentionBean intentionBean);
        void updata(MyIntentionBean intentionBean);
    }

    interface fcPersenter extends BasePresenter<fcView>{
        void getData(String statusId,int page, int size);
        void addData(String statusId,int page, int size);
    }

}
