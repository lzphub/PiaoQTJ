package cn.dankal.home.persenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.PostRequestBean;
import cn.dankal.basiclib.bean.ProductClassifyBean;

public interface PostRequestContact {

    interface pcview extends BaseView{
        void postSuccess();
    }

    interface productPresenter extends BasePresenter<pcview>{
        void post(PostRequestBean postRequestBean);
    }

}
