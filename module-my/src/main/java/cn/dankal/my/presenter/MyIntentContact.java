package cn.dankal.my.presenter;

import java.util.List;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.GetIntentionBean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.ProductListBean;

public interface MyIntentContact {

    interface fcView extends BaseView{
        void getDataSuccess(MyIntentionBean intentionBean);
        void updata(MyIntentionBean intentionBean);
    }

    interface fcPersenter extends BasePresenter<fcView>{
        void getData(GetIntentionBean getIntentionBean);
        void addData(GetIntentionBean getIntentionBean);
    }

}
