package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductTabBean;
import cn.dankal.basiclib.util.image.PicUtils;

public class ProductTabRvAdapter extends BaseRecyclerViewAdapter<ProductClassifyBean.RootBean.ChildrenBean> {
    private ImageView productImg;
    private TextView productName;

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.product_list_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<ProductClassifyBean.RootBean.ChildrenBean> {

        public MyViewHolder(View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name);
        }

        @Override
        public void onBindData(ProductClassifyBean.RootBean.ChildrenBean data, int position) {
            Glide.with(context).load(PicUtils.getUrl(data.getImage())).into(productImg);
            productName.setText(data.getName());
        }
    }
}
