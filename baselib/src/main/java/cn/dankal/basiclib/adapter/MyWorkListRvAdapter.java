package cn.dankal.basiclib.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.api.MyService;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.dankal.basiclib.bean.MyWorkListBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.StateUtil;
import cn.dankal.basiclib.util.StringUtil;

public class MyWorkListRvAdapter extends BaseRecyclerViewAdapter<MyWorkListBean.DataBean> {

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.my_worklist_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<MyWorkListBean.DataBean> {
        @BindView(R2.id.status_text)
        TextView statusText;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.content_tv)
        TextView contentTv;
        @BindView(R2.id.price_text)
        TextView priceText;
        @BindView(R2.id.bt_finish)
        Button btFinish;
        @BindView(R2.id.rl_finish)
        RelativeLayout rlFinish;


        public MyViewHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void onBindData(MyWorkListBean.DataBean data, int position) {
            statusText.setText(StateUtil.WorkListState(data.getStatus()));
            if(data.getStatus()==5 || data.getStatus()==8){
                statusText.setTextColor(Color.parseColor("#FE3824"));
            }
            titleTv.setText(data.getName());
            contentTv.setText(data.getDesc());
            priceText.setText("$" + StringUtil.isDigits(data.getStart_price()) + " ~ " + StringUtil.isDigits(data.getEnd_price()));
            if(data.getStatus()==4){
                rlFinish.setVisibility(View.VISIBLE);
            }else if(data.getStatus()==8){
                rlFinish.setVisibility(View.VISIBLE);
                btFinish.setText("重新提交");
            }
            btFinish.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.FINISHWORK).withString("project_uuid",data.getUuid()).withString("plan_uuid",data.getPlan_uuid()).navigation());
        }
    }
}
