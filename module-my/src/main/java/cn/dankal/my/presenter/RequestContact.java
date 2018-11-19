package cn.dankal.my.presenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.IntentionDateBean;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import cn.dankal.basiclib.util.StringUtil;

public interface RequestContact {
    interface RequestView extends BaseView{
        void getDataSuccess(MyRequestBean myRequestBean);
        void updata(MyRequestBean myRequestBean);
        void getRequestDataSuccess(RequestDataBean requestDataBean);
    }
    interface idPresenter extends BasePresenter<RequestView>{
        void getData(int page_index,int page_size);
        void delete(String demand_id);
        void getRequestData(String demand_id);
    }
}
