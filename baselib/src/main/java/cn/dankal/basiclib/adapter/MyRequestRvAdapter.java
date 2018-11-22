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
import cn.dankal.basiclib.base.recyclerview.del.BaseDelViewHolder;
import cn.dankal.basiclib.bean.MyRequestBean;

public class MyRequestRvAdapter extends BaseRecyclerViewAdapter<MyRequestBean.databean> {

    private List<String> urllist = new ArrayList<>();

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_request_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseDelViewHolder<MyRequestBean.databean> {
        @BindView(R2.id.request_name)
        TextView requestName;
        @BindView(R2.id.be_add_image)
        RecyclerView beAddImage;
        @BindView(R2.id.request_price)
        TextView requestPrice;
        @BindView(R2.id.requect_data)
        TextView requectData;
        private InternalImgRvAdapter internalImgRvAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            beAddImage.setLayoutManager(linearLayoutManager);
        }

        @Override
        public void onBindData(MyRequestBean.databean data, int position) {
            internalImgRvAdapter =new InternalImgRvAdapter();
            beAddImage.setNestedScrollingEnabled(false);
            beAddImage.setHasFixedSize(true);
            beAddImage.setAdapter(internalImgRvAdapter);
            internalImgRvAdapter.updateData(data.getImages());
            requestName.setText(data.getTitle());
            requectData.setText(data.getStart_date() + "~" + data.getEnd_date());
            requestPrice.setText("$" + data.getStart_price() + "~" + data.getEnd_price());
        }
    }

    @Override
    protected void setUpItemEvent(BaseRecyclerViewHolder holder) {
    }
}


