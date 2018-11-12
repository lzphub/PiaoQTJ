package cn.dankal.home.persenter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.DemandListbean;

public class DemandListPersenter extends BaseRecyclerViewPresenter<DemandListbean> {

    private List<DemandListbean> demandListbeanList = new ArrayList<>();

    @Override
    public void requestData(String pageIndex) {

        for (int i = 0; i < 10; i++) {
            DemandListbean demandListbean = new DemandListbean();
            demandListbeanList.add(demandListbean);
        }
        view.render(demandListbeanList);
    }
}
