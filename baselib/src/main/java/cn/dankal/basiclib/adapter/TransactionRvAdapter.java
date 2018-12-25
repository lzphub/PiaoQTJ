package cn.dankal.basiclib.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.TransactionBean;

public class TransactionRvAdapter extends BaseRecyclerViewAdapter<TransactionBean.DataBean> {



    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.transaction_list_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<TransactionBean.DataBean> {

        @BindView(R2.id.type_img)
        ImageView typeImg;
        @BindView(R2.id.type_text)
        TextView typeText;
        @BindView(R2.id.time_text)
        TextView timeText;
        @BindView(R2.id.transaction_Amount)
        TextView transactionAmount;

        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(TransactionBean.DataBean data, int position) {
            typeText.setText(data.getType());
            timeText.setText(data.getCreate_time());
            if(data.getType().equals("提现")){
                typeImg.setImageResource(R.mipmap.pic_profit_record_withdrawcash);
                transactionAmount.setText("-"+data.getMoney());
                transactionAmount.setTextColor(Color.parseColor("#FE3824"));
            }else{
                transactionAmount.setTextColor(Color.parseColor("#528E1A"));
                transactionAmount.setText("+"+data.getMoney());
            }
        }
    }
}
