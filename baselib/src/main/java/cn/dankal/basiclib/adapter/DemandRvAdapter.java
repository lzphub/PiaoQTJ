package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.DemandListbean;

public class DemandRvAdapter extends BaseRecyclerViewAdapter<DemandListbean.DataBean> {



    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.demand_list_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<DemandListbean.DataBean> {

        @BindView(R2.id.demand_price)
        TextView demandPrice;
        @BindView(R2.id.re_date)
        TextView reDate;
        @BindView(R2.id.demand_title)
        TextView demandTitle;
        @BindView(R2.id.demand_content)
        TextView demandContent;

        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(DemandListbean.DataBean data, int position) {
            demandPrice.setText("¥"+data.getStart_price()+"~"+data.getEnd_price());
            reDate.setText(data.getCpl_start_date()+"~"+data.getCpl_end_date());
            demandTitle.setText(data.getName());
            demandContent.setText(data.getDesc());
        }
    }
}
