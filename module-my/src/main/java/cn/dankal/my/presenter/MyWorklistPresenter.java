package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import api.MyServiceFactory;
import cn.dankal.basiclib.base.BaseRvFragmentImp;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import io.reactivex.disposables.Disposable;

public class MyWorklistPresenter extends BaseRecyclerViewPresenter<MyWorkListBean.DataBean> {

    private static MyWorklistPresenter worklistPresenter=null;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public static synchronized MyWorklistPresenter getWorklistPresenter(){
        if(worklistPresenter==null){
            worklistPresenter=new MyWorklistPresenter();
        }
        return worklistPresenter;
    }

    @Override
    public void requestData(String pageIndex) {
        Logger.d("fragment",status);
        HomeServiceFactory.getWorkList(pageIndex, "10", status).safeSubscribe(new AbstractDialogSubscriber<MyWorkListBean>(mView) {
            @Override
            public void onNext(MyWorkListBean myWorkListBean) {
                mView.render(myWorkListBean.getData());
                Logger.d("fragmentOnnext",status);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.d("fragmenterror",status);
            }


        });
    }
}
