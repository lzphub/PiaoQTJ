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
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;

public class ProductSearchRvAdapter extends BaseRecyclerViewAdapter<ProductListBean.DataBean> {


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.home_product_list_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<ProductListBean.DataBean> {
        @BindView(R2.id.product_img)
        ImageView productImg;
        @BindView(R2.id.product_price)
        TextView productPrice;
        @BindView(R2.id.product_name)
        TextView productName;
        @BindView(R2.id.product_content)
        TextView productContent;


        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(ProductListBean.DataBean data, int position) {
            Glide.with(context).load(data.getImages().get(0)).into(productImg);
            productPrice.setText(data.getPrice());
            productName.setText(data.getName());
//            productContent.setText(data.get);
        }
    }
}
