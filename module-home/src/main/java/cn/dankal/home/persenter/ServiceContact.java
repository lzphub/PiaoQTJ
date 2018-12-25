package cn.dankal.home.persenter;

import java.util.List;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.MyEarBean;
import cn.dankal.basiclib.bean.NewServiceMsgBean;
import cn.dankal.basiclib.bean.ProductClassifyBean;

public interface ServiceContact {

    interface pcview extends BaseView{
        void sendMsgSuccess(int type);
        void sendMsgFail(int type);
        void getMsgRecord(List<ChatBean.DataBean> dataBean,boolean isLastPage);
        void addmoreSuccess(List<ChatBean.DataBean> dataBean,boolean isLastPage);
        void getUserMsgRecord(List<ChatBean.DataBean> dataBean,boolean isLastPage);
        void userAddMoreSuccess(List<ChatBean.DataBean> dataBean,boolean isLastPage);
        void getNewMsg(List<NewServiceMsgBean.NewMsgBean> newMsgBeans);
    }

    interface productPresenter extends BasePresenter<pcview>{
        void sendMsg(String content,int type);
        void getMsgRecord(String page_index,String page_size);
        void addmore(String page_index,String page_size);
        void getNewMsg();
        void getUserNewMsg();
        void userSendMsg(String content,int type);
        void getUserMsgRecord(String page_index,String page_size);
        void userAddMore(String page_index,String page_size);
    }

}
