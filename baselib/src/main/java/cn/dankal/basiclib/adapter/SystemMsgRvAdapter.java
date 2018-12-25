package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.SystemMsgBean;

public class SystemMsgRvAdapter extends BaseRecyclerViewAdapter<SystemMsgBean.DataBean> {

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_systemmsg_rv;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<SystemMsgBean.DataBean> {
        @BindView(R2.id.tv_title)
        TextView tvTitle;
        @BindView(R2.id.tv_date)
        TextView tvDate;
        @BindView(R2.id.tv_content)
        TextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(SystemMsgBean.DataBean data, int position) {
            tvContent.setText(data.getDescription());
            tvDate.setText(data.getPush_time());
            tvTitle.setText(data.getTitle());
        }
    }
}
