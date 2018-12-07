package cn.dankal.basiclib.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.dankal.basiclib.DankalApplication;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.base.recyclerview.del.BaseDelViewHolder;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.StateUtil;

public class MyRequestRvAdapter extends BaseRecyclerViewAdapter<MyRequestBean.databean> {


    private List<String> urllist = new ArrayList<>();

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_request_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseDelViewHolder<MyRequestBean.databean> {
        @BindView(R2.id.request_name)
        TextView requestName;
        @BindView(R2.id.be_add_image)
        RecyclerView beAddImage;
        @BindView(R2.id.request_price)
        TextView requestPrice;
        @BindView(R2.id.requect_data)
        TextView requectData;
        @BindView(R2.id.state_text)
        TextView stateText;
        private InternalImgRvAdapter internalImgRvAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            beAddImage.setLayoutManager(linearLayoutManager);
            beAddImage.setNestedScrollingEnabled(false);
            beAddImage.setHasFixedSize(true);
        }

        @Override
        public void onBindData(MyRequestBean.databean data, int position) {
            internalImgRvAdapter = new InternalImgRvAdapter();
            beAddImage.setAdapter(internalImgRvAdapter);
            internalImgRvAdapter.updateData(data.getImages());
            requestName.setText(data.getTitle());
            if(data.getStart_price()==null || data.getEnd_date()==null){
                requectData.setText("");
            }else{
                requectData.setText(data.getStart_date() + "~" + data.getEnd_date());
            }
            requestPrice.setText("$" + data.getStart_price() + "~" + data.getEnd_price());
            stateText.setText(StateUtil.requestState(data.getStatus()));

            MyRequestBean.databean data2=data;

            internalImgRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<String>() {
                @Override
                public void onItemClick(View v, int position, String data) {
                    ARouter.getInstance().build(MyProtocol.MYREQUESTDETA).withString("demand_id",data2.getDemand_id()).navigation();
                }
            });
        }
    }

    @Override
    protected void setUpItemEvent(BaseRecyclerViewHolder holder) {
    }
}


