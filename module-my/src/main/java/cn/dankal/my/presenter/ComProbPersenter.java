package cn.dankal.my.presenter;

import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class ComProbPersenter extends BaseRecyclerViewPresenter<ComProbBean.DataBean> {

    private List<String> stringList = new ArrayList<>();

    @Override
    public void requestData(String pageIndex) {
        MyServiceFactory.getUserFaqList(Integer.valueOf(pageIndex),10).safeSubscribe(new AbstractDialogSubscriber<ComProbBean>(mView) {
            @Override
            public void onNext(ComProbBean comProbBean) {
                mView.render(comProbBean.getData());
            }
        });
    }
}
