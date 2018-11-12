package cn.dankal.my.presenter;

import java.util.ArrayList;
import java.util.List;

import api.UserServiceFactory;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.MyRequestBean;

public class MyRequestPresenter extends BaseRecyclerViewPresenter<MyRequestBean> {

    private List<MyRequestBean> requestBeans=new ArrayList<>();
    private List<MyRequestBean.urli> urliList=new ArrayList<>();
    @Override
    public void requestData(String pageIndex) {
        for(int x=0;x<3;x++){
            MyRequestBean.urli urli=new MyRequestBean.urli();
            urli.setImgurl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg");
            urliList.add(urli);
        }
        for(int i=0;i<4;i++){
            MyRequestBean myRequestBean=new MyRequestBean();
            myRequestBean.setUrlString(urliList);
            requestBeans.add(myRequestBean);
        }
        view.render(requestBeans);
    }

}
