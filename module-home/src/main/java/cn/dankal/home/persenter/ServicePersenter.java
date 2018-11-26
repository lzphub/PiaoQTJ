package cn.dankal.home.persenter;

import java.util.ArrayList;
import java.util.List;

import api.HomeServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;

public class DemandListPersenter extends BaseRecyclerViewPresenter<DemandListbean.DataBean> {

    @Override
    public void requestData(String pageIndex) {
        HomeServiceFactory.getDemandList(Integer.valueOf(pageIndex),10).safeSubscribe(new AbstractDialogSubscriber<DemandListbean>(mView) {
            @Override
            public void onNext(DemandListbean demandListbean) {
                mView.render(demandListbean.getData());
            }
        });
    }
}
