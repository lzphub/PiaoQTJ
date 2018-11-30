package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.NewServiceMsgBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;

public class ServicePersenter implements ServiceContact.productPresenter {

    private ServiceContact.pcview pcview;

    private List<ChatBean.DataBean> dataBeanList;

    @Override
    public void attachView(@NonNull ServiceContact.pcview view) {
        pcview=view;
    }

    @Override
    public void detachView() {
        pcview=null;
    }

    @Override
    public void sendMsg(String content, int type) {
        MyServiceFactory.serviceSendMsg(content,type).safeSubscribe(new AbstractDialogSubscriber<String>(pcview) {
            @Override
            public void onNext(String s) {
                pcview.sendMsgSuccess(type);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                pcview.sendMsgFail(type);
            }
        });
    }

    @Override
    public void getMsgRecord(String page_index,String page_size) {
        MyServiceFactory.getMsgRecord(page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<ChatBean>(pcview) {
            @Override
            public void onNext(ChatBean chatBean) {
                dataBeanList=new ArrayList<>();
                for(int i=chatBean.getData().size()-1;i>-1;i--){
                    dataBeanList.add(chatBean.getData().get(i));
                }
                if(chatBean.getCurrent_page()==chatBean.getLast_page()){
                    pcview.getMsgRecord(dataBeanList,true);
                }else{
                    pcview.getMsgRecord(dataBeanList,false);
                }
            }
        });
    }

    @Override
    public void addmore(String page_index, String page_size) {
        MyServiceFactory.getMsgRecord(page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<ChatBean>(pcview) {
            @Override
            public void onNext(ChatBean chatBean) {
                dataBeanList=new ArrayList<>();
                for(int i=chatBean.getData().size()-1;i>-1;i--){
                    dataBeanList.add(chatBean.getData().get(i));
                }
                if(chatBean.getCurrent_page()==chatBean.getLast_page()){
                    pcview.addmoreSuccess(dataBeanList,true);
                }else{
                    pcview.addmoreSuccess(dataBeanList,false);
                }
            }
        });
    }

    @Override
    public void getNewMsg() {
        MyServiceFactory.getEngNewServiceMsg().safeSubscribe(new AbstractDialogSubscriber<NewServiceMsgBean>(pcview) {
            @Override
            public void onNext(NewServiceMsgBean newServiceMsgBean) {
                if(null!=newServiceMsgBean.getNew_msg()){
                    pcview.getNewMsg(newServiceMsgBean.getNew_msg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.d("errorrrr",e);
            }
        });
    }

    @Override
    public void getUserNewMsg() {
        MyServiceFactory.getNewServiceMsg().safeSubscribe(new AbstractDialogSubscriber<NewServiceMsgBean>(pcview) {
            @Override
            public void onNext(NewServiceMsgBean newServiceMsgBean) {
                if(null!=newServiceMsgBean.getNew_msg()){
                    pcview.getNewMsg(newServiceMsgBean.getNew_msg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.d("errrorrrr",e);
            }
        });
    }


    @Override
    public void userSendMsg(String content, int type) {
        MyServiceFactory.userServiceSendMsg(content,type).safeSubscribe(new AbstractDialogSubscriber<String>(pcview) {
            @Override
            public void onNext(String s) {

            }
        });
    }

    @Override
    public void getUserMsgRecord(String page_index, String page_size) {
        MyServiceFactory.getUserMsgRecord(page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<ChatBean>(pcview) {
            @Override
            public void onNext(ChatBean chatBean) {
                dataBeanList=new ArrayList<>();
                for(int i=chatBean.getData().size()-1;i>-1;i--){
                    dataBeanList.add(chatBean.getData().get(i));
                }
                if(chatBean.getCurrent_page()==chatBean.getLast_page()){
                    pcview.getMsgRecord(dataBeanList,true);
                }else{
                    pcview.getMsgRecord(dataBeanList,false);
                }
            }
        });
    }

    @Override
    public void userAddMore(String page_index, String page_size) {
        MyServiceFactory.getUserMsgRecord(page_index,page_size).safeSubscribe(new AbstractDialogSubscriber<ChatBean>(pcview) {
            @Override
            public void onNext(ChatBean chatBean) {
                dataBeanList=new ArrayList<>();
                for(int i=chatBean.getData().size()-1;i>-1;i--){
                    dataBeanList.add(chatBean.getData().get(i));
                }
                if(chatBean.getCurrent_page()==chatBean.getLast_page()){
                    pcview.userAddMoreSuccess(dataBeanList,true);
                }else{
                    pcview.userAddMoreSuccess(dataBeanList,false);
                }
            }
        });
    }
}
