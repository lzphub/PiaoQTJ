package cn.dankal.my.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.ProductListBean;

public class MyFavoritePresenter extends BaseRecyclerViewPresenter<ProductListBean> {

    private List<ProductListBean> productListBeanList=new ArrayList<>();
    @Override
    public void requestData(String pageIndex) {
        for(int i=0;i<10;i++){
            ProductListBean productListBean=new ProductListBean();
            productListBeanList.add(productListBean);
        }
        view.render(productListBeanList);
    }
}
