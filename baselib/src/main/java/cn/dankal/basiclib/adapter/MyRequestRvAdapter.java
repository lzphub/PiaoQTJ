package cn.dankal.basiclib.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.MyRequestBean;

public class MyRequestRvAdapter extends BaseRecyclerViewAdapter<MyRequestBean> {

    private List<MyRequestBean.urli> urliList = new ArrayList<>();
    private InternalImgRvAdapter onlyImgRvAdapter;

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_request_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyRequestBean> {
        @BindView(R2.id.request_name)
        TextView requestName;
        @BindView(R2.id.be_add_image)
        RecyclerView beAddImage;
        @BindView(R2.id.request_price)
        TextView requestPrice;
        @BindView(R2.id.requect_data)
        TextView requectData;

        public MyViewHolder(View itemView) {
            super(itemView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            beAddImage.setLayoutManager(linearLayoutManager);
            onlyImgRvAdapter = new InternalImgRvAdapter();
        }

        @Override
        public void onBindData(MyRequestBean data, int position) {
            requestName.setText(data.getTitle());
            requectData.setText(data.getData());
            requestPrice.setText("$" + data.getPrice());
            urliList = data.getUrlString();
            onlyImgRvAdapter.addMore(urliList);
            beAddImage.setAdapter(onlyImgRvAdapter);
        }
    }
}
