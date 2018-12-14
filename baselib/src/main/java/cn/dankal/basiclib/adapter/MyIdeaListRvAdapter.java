package cn.dankal.basiclib.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyIdeaListBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.StateUtil;

public class MyIdeaListRvAdapter extends BaseRecyclerViewAdapter<MyIdeaListBean.DataBean> {


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_myidea_list;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyIdeaListBean.DataBean> {
        @BindView(R2.id.tv_state)
        TextView tvState;
        @BindView(R2.id.tv_title)
        TextView tvTitle;
        @BindView(R2.id.tv_content)
        TextView tvContent;
        @BindView(R2.id.rv_imgs)
        RecyclerView rvImgs;
        private OnlyImgRvAdapter onlyImgRvAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(MyIdeaListBean.DataBean data, int position) {
            tvState.setText(StateUtil.ideaState(data.getStatus()));
            if(data.getStatus()==2){
                tvState.setTextColor(Color.parseColor("#FE3824"));
            }else{
                tvState.setTextColor(Color.parseColor("#141414"));
            }
            tvTitle.setText(data.getTitle());
            tvContent.setText(data.getDetail());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvImgs.setLayoutManager(linearLayoutManager);
            rvImgs.setNestedScrollingEnabled(false);
            rvImgs.setHasFixedSize(true);
            onlyImgRvAdapter = new OnlyImgRvAdapter();
            onlyImgRvAdapter.addMore(data.getImages());
            rvImgs.setAdapter(onlyImgRvAdapter);

            MyIdeaListBean.DataBean dataBean=data;

            onlyImgRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<String>() {
                @Override
                public void onItemClick(View v, int position, String data) {
                    ARouter.getInstance().build(MyProtocol.IDEADATA).withSerializable("data", dataBean).navigation();                }
            });

        }
    }
}
