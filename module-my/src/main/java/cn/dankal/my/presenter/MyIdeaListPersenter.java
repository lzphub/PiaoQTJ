package cn.dankal.my.presenter;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyIdeaListBean;
import cn.dankal.basiclib.bean.TransactionBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class MyIdeaListPersenter extends BaseRecyclerViewPresenter<MyIdeaListBean.DataBean> {

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void requestData(String pageIndex) {
        MyServiceFactory.getMyIdeaList(status,Integer.valueOf(pageIndex),20).safeSubscribe(new AbstractDialogSubscriber<MyIdeaListBean>(mView) {
            @Override
            public void onNext(MyIdeaListBean myIdeaListBean) {
                mView.render(myIdeaListBean.getData());
            }
        });
    }
}
