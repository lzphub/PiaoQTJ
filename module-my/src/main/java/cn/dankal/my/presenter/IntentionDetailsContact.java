package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.IntentionDateBean;

public interface IntentionDetailsContact {
    interface idView extends BaseView{
        void getDataSuccess(IntentionDateBean intentionDateBean);
    }
    interface idPresenter extends BasePresenter<idView>{
        void getData(String intention_id);
    }
}
