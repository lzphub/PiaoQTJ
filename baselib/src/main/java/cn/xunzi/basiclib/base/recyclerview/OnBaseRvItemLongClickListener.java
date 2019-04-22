package cn.xunzi.basiclib.base.recyclerview;

import android.view.View;

public abstract class OnBaseRvItemLongClickListener<T, V extends BaseRecyclerViewHolder<T>> {
    public boolean onItemLongClick(V holder, View v, int position, T data) {
        return onItemLongClick(v, position, data);
    }

    public abstract boolean onItemLongClick(View v, int position, T data);

}
