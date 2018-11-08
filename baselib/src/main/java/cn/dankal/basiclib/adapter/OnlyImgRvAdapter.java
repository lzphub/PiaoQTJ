package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URL;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.util.Logger;

public class OnlyImgRvAdapter extends BaseRecyclerViewAdapter<String> {


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.image_only_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<String> {
        @BindView(R2.id.content_img)
        ImageView contentImg;

        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(String url, int position) {
            Logger.d("urllllll",url);
            Glide.with(context).load(url).into(contentImg);
        }
    }
}
