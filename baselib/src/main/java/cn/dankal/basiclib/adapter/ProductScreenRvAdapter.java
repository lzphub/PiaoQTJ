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
import cn.dankal.basiclib.bean.ProductListBean;

public class ProductScreenRvAdapter extends BaseRecyclerViewAdapter<ProductListBean> {


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.product_screen_rvitem;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<ProductListBean> {
        @BindView(R2.id.product_img)
        ImageView productImg;
        @BindView(R2.id.product_price)
        TextView productPrice;
        @BindView(R2.id.product_name)
        TextView productName;


        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(ProductListBean data, int position) {
            Glide.with(context).load(data.getData().get(position).getImages().get(0)).into(productImg);
            productPrice.setText("$"+data.getData().get(position).getPrice());
            productName.setText(data.getData().get(position).getName());
        }
    }
}
