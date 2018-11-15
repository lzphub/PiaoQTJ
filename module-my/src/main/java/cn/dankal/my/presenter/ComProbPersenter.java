package cn.dankal.my.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;

public class ComProbPersenter extends BaseRecyclerViewPresenter<String> {

    private List<String> stringList = new ArrayList<>();

    @Override
    public void requestData(String pageIndex) {
        for (int i = 0; i < 10; i++) {
            stringList.add("常见问题" + i);
        }
        mView.render(stringList);
    }
}
