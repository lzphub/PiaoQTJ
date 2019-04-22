package cn.xunzi.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xunzi.basiclib.GlideApp;
import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.xunzi.basiclib.bean.HomeRecBean;

public class Home_Rec_Adapter extends BaseRecyclerViewAdapter<HomeRecBean> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.home_rec_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<HomeRecBean> {

     TextView textView,num;
     ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
            imageView=itemView.findViewById(R.id.image);
            num=itemView.findViewById(R.id.tv_num);
        }

        @Override
        public void onBindData(HomeRecBean data, int position) {
            textView.setText(data.getText());
            GlideApp.with(context).load(data.getImage()).into(imageView);
            if(position!=5){
                num.setVisibility(View.GONE);
            }else{
                if(data.getNum().equals("0")){
                    num.setVisibility(View.GONE);
                }else{
                    num.setText(data.getNum());
                }
            }
        }

    }
}
