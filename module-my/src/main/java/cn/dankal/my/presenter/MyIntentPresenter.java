package cn.dankal.my.presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyIntentionBean;

public class MyIntentPresenter extends BaseRecyclerViewPresenter<MyIntentionBean>{

    private List<MyIntentionBean> intentionBeans=new ArrayList<>();

    @Override
    public void requestData(String pageIndex) {
        for(int i=0;i<5;i++){
            MyIntentionBean myIntentionBean=new MyIntentionBean();
            intentionBeans.add(myIntentionBean);
        }
        view.render(intentionBeans);
    }


}
