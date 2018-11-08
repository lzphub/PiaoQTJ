package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.MyIntentionBean;

public class MyIntentionRvAdapter extends BaseRecyclerViewAdapter<MyIntentionBean> {



    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_intention_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyIntentionBean> {
        @BindView(R2.id.intention_img)
        ImageView intentionImg;
        @BindView(R2.id.intention_name)
        TextView intentionName;
        @BindView(R2.id.intention_price)
        TextView intentionPrice;
        @BindView(R2.id.intention_state)
        TextView intentionState;
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(MyIntentionBean data, int position) {
            Glide.with(context).load(data.getImgurl()).into(intentionImg);
            intentionName.setText(data.getName());
            intentionPrice.setText(data.getPrice());
            intentionState.setText(data.getState());
        }
    }
}
