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
import cn.dankal.basiclib.util.StateUtil;
import cn.dankal.basiclib.util.image.PicUtils;

public class MyIntentionRvAdapter extends BaseRecyclerViewAdapter<MyIntentionBean.DataBean> {



    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_intention_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyIntentionBean.DataBean> {
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
        public void onBindData(MyIntentionBean.DataBean data, int position) {
            Glide.with(context).load(PicUtils.getUrl(data.getImages().get(0))).into(intentionImg);
            intentionName.setText(data.getProduct_name());
            intentionPrice.setText("$"+data.getProduct_price());
            intentionState.setText(StateUtil.intentionStatus(data.getStatus()));
        }
    }
}
