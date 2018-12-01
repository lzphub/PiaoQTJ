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
import cn.dankal.basiclib.util.image.PicUtils;

public class ProductScreenRvAdapter extends BaseRecyclerViewAdapter<ProductHomeListBean.DataBean> {


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.product_screen_rvitem;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<ProductHomeListBean.DataBean> {
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
        public void onBindData(ProductHomeListBean.DataBean data, int position) {
            Glide.with(context).load(PicUtils.getUrl(data.getImages().get(0))).into(productImg);
            productPrice.setText("$"+data.getPrice());
            productName.setText(data.getName());
        }
    }
}
