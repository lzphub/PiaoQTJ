package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.base.recyclerview.del.BaseDelViewHolder;
import cn.dankal.basiclib.bean.MyEarBean;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.CircleImageView;

public class MyEarAdapter extends BaseRecyclerViewAdapter<MyEarBean.ChartBean> {

    private int layout;

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_ear_top3_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseDelViewHolder<MyEarBean.ChartBean> {

        private TextView rank,name,balance;
        private CircleImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            rank=itemView.findViewById(R.id.rank_text);
            name=itemView.findViewById(R.id.name_text);
            balance=itemView.findViewById(R.id.add_balance);
            avatar=itemView.findViewById(R.id.avatar);
        }

        @Override
        public void onBindData(MyEarBean.ChartBean data, int position) {
            super.onBindData(data, position);
                switch (data.getRank()){
                    case 1:
                        rank.setBackgroundResource(R.mipmap.pic_profit_rankings_first);
                        break;
                    case 2:
                        rank.setBackgroundResource(R.mipmap.pic_profit_rankings_second);
                        break;
                    case 3:
                        rank.setBackgroundResource(R.mipmap.pic_profit_rankings_third);
                        break;
                }
            name.setText(data.getName());
            balance.setText("+"+data.getTotal_commission());
            PicUtils.loadAvatar(data.getAvatar(),avatar);
        }
    }
}
