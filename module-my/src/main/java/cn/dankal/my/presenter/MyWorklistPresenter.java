package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyWorklistPresenter implements MyWorkListContact.mwPersenter{

    private MyWorkListContact.mwView mwView;
    private static MyWorklistPresenter worklistPresenter=null;

    public static synchronized MyWorklistPresenter getWorklistPresenter(){
        if(worklistPresenter==null){
            worklistPresenter=new MyWorklistPresenter();
        }
        return worklistPresenter;
    }

    @Override
    public void getData(String page, String size, String status) {
        HomeServiceFactory.getWorkList(page, size, status).safeSubscribe(new AbstractDialogSubscriber<MyWorkListBean>(mwView) {
            @Override
            public void onNext(MyWorkListBean myWorkListBean) {
                mwView.getDataSuccess(myWorkListBean);
            }
        });
    }

    @Override
    public void attachView(@NonNull MyWorkListContact.mwView view) {
        mwView=view;
    }

    @Override
    public void detachView() {
        mwView=null;
    }
}
