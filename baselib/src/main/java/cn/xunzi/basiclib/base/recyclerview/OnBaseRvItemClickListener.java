package cn.xunzi.basiclib.base.recyclerview;

import android.view.View;

public abstract class OnBaseRvItemClickListener<T, V extends BaseRecyclerViewHolder<T>> {
    public void onItemClick(V holder, View v, int position, T data) {
        onItemClick(v, position, data);
    }

    public abstract void onItemClick(View v, int position, T data);
}
