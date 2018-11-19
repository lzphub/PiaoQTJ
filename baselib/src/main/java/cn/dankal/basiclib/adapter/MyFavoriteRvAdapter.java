package cn.dankal.basiclib.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.base.recyclerview.del.BaseDelViewHolder;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.ProductListBean;

public class MyFavoriteRvAdapter extends BaseRecyclerViewAdapter<ProductListBean.DataBean> {



    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_fav_list_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseDelViewHolder<ProductListBean.DataBean> {
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
        public void onBindData(ProductListBean.DataBean dataBean, int position) {
            productName.setText(dataBean.getName());
            productPrice.setText("$"+dataBean.getPrice());
            productContent.setText(dataBean.getDescription());
        }
    }

    @Override
    protected void setUpItemEvent(BaseRecyclerViewHolder holder) {
    }
}


