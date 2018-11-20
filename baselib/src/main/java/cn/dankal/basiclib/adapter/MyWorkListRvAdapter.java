package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.MyWorkListBean;

public class MyWorkListRvAdapter extends BaseRecyclerViewAdapter<MyWorkListBean.DataBean> {

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_worklist_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyWorkListBean.DataBean> {
        @BindView(R2.id.status_text)
        TextView statusText;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.content_tv)
        TextView contentTv;
        @BindView(R2.id.price_text)
        TextView priceText;


        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(MyWorkListBean.DataBean data, int position) {
//            statusText.setText(data.get);
            titleTv.setText(data.getTitle());
            contentTv.setText(data.getContent());
            priceText.setText("$"+data.getStart_price()+"~"+data.getEnd_price());
        }
    }
}
