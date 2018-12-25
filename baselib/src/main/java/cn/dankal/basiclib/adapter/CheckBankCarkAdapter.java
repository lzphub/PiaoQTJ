package cn.dankal.basiclib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.bean.BankCardListBean;

public class CheckBankCarkAdapter extends RecyclerView.Adapter {

    private BankCardListBean datas;

    private int selected = -1;

//    public CheckBankCarkAdapter(BankCardListBean datas) {
//        this.datas = datas;
//    }

    public void updata(BankCardListBean datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_bankcark_item, parent, false);

        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SingleViewHolder) {
            final SingleViewHolder viewHolder = (SingleViewHolder) holder;
            viewHolder.cardName.setText(datas.getCards().get(position).getBank_name());
            String num = datas.getCards().get(position).getCard_number();
            viewHolder.cardNum.setText("尾号" + num.substring(num.length() - 4, num.length()) + "储蓄卡");

            if (selected == position) {
                viewHolder.checkImg.setVisibility(View.VISIBLE);
                viewHolder.itemView.setSelected(true);
            } else {
                viewHolder.checkImg.setVisibility(View.INVISIBLE);
                viewHolder.itemView.setSelected(false);
            }

            if (mOnItemClickLitener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickLitener.onLongClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.getCards().size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        TextView cardName, cardNum;
        ImageView checkImg;

        public SingleViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView) itemView.findViewById(R.id.card_name);
            cardNum = (TextView) itemView.findViewById(R.id.card_num);
            checkImg = (ImageView) itemView.findViewById(R.id.check_img);
        }
    }

}
