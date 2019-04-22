package cn.xunzi.basiclib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.xunzi.basiclib.GlideApp;
import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.xunzi.basiclib.base.recyclerview.BaseRecyclerViewHolder;
import cn.xunzi.basiclib.bean.HomeRecBean;
import cn.xunzi.basiclib.bean.TaskBean;

public class TaskListAdapter extends BaseRecyclerViewAdapter<TaskBean.DataEntity.ListEntity> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.tasklist_item;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new MyViewHolder(rootView);
    }

    class MyViewHolder extends BaseRecyclerViewHolder<TaskBean.DataEntity.ListEntity> {

        TextView name,money,number,status;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_task_name);
            imageView=itemView.findViewById(R.id.iv_logo);
            money=itemView.findViewById(R.id.tv_money);
            number=itemView.findViewById(R.id.tv_number);
            status=itemView.findViewById(R.id.tv_status);
        }

        @Override
        public void onBindData(TaskBean.DataEntity.ListEntity data, int position) {
            name.setText(data.getTitle());
            money.setText("奖金："+data.getReward()+"元");
            number.setText("剩余:"+data.getRemain());
            if(Integer.valueOf(data.getUserStatue())==1){
                status.setText("已领取");
                status.setBackgroundResource(R.drawable.login_btn_bg_b7);
            }else{
                status.setText("未领取");
            }
            if(Integer.valueOf(data.getType())==2){
                Glide.with(context).load(R.mipmap.putongrenwu).into(imageView);
            }else if(Integer.valueOf(data.getType())==3){
                Glide.with(context).load(R.mipmap.gaojirenwu).into(imageView);
            }
        }

    }
}
