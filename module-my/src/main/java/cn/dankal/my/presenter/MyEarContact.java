package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.bean.MyEarBean;

public interface MyEarContact {
    interface meView extends BaseView{
        void getDataSuccess(MyEarBean myEarBean);
    }
    interface imePresenter extends BasePresenter<meView>{
        void getData();
    }
}
