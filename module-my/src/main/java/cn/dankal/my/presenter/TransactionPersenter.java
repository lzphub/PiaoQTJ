package cn.dankal.my.presenter;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.bean.TransactionBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class TransactionPersenter extends BaseRecyclerViewPresenter<TransactionBean.DataBean> {

    @Override
    public void requestData(String pageIndex) {
       MyServiceFactory.getTransactionList(Integer.valueOf(pageIndex),10).safeSubscribe(new AbstractDialogSubscriber<TransactionBean>(mView) {
           @Override
           public void onNext(TransactionBean transactionBean) {
               mView.render(transactionBean.getData());
           }
       });
    }
}
