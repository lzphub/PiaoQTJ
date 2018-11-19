package cn.dankal.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;

public class TextOnlyAdapter extends BaseRecyclerViewAdapter<String> {

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.text_only_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView,viewType);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<String> {

        TextView title;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView, viewType);
            title=itemView.findViewById(R.id.item_title);
        }

        @Override
        public void onBindData(String data, int position) {
            title.setText(data);
        }
    }
}
