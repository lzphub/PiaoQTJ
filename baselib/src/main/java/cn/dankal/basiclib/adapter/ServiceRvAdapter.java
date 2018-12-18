package cn.dankal.basiclib.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.bean.ChatBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.util.image.PicUtils;

public class ServiceRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ChatBean.DataBean> serviceTextBeanList1 = new ArrayList<>();
    private String picurl;
    private Context context;

    public ServiceRvAdapter(List<ChatBean.DataBean> serviceTextBeanList, Context context) {
        this.serviceTextBeanList1 = serviceTextBeanList;
        this.context = context;
    }

    public void update(List<ChatBean.DataBean> serviceTextBeanList, String pic) {
        serviceTextBeanList1=new ArrayList<>();
        this.serviceTextBeanList1 = serviceTextBeanList;
        this.picurl = pic;
        notifyDataSetChanged();
    }
    public void addmore(List<ChatBean.DataBean> serviceTextBeanList, String pic) {
        this.serviceTextBeanList1 .addAll(0,serviceTextBeanList);
        this.picurl = pic;
        notifyDataSetChanged();
    }

    public void addSendData(ChatBean.DataBean dataBean,String pic){
        this.serviceTextBeanList1 .add(dataBean);
        this.picurl = pic;
        notifyItemInserted(serviceTextBeanList1.size());
    }

    public String getPicurl(int position){
       return serviceTextBeanList1.get(position).getContent();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.service_text_rv_item, parent, false);
                viewHolder = new MyViewHolder(view);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.service_customer_text_rv_item, parent, false);
                viewHolder = new MyViewHolder(view);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.service_image_rv_item, parent, false);
                viewHolder = new MyImgHolder(view);
                break;
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.service_customer_image_rv_item, parent, false);
                viewHolder = new MyImgHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBean.DataBean serviceTextBean = serviceTextBeanList1.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                setTextMsg((MyViewHolder) holder, serviceTextBean);
                break;
            case 2:
                setTextMsg((MyViewHolder) holder, serviceTextBean);
                break;
            case 3:
                setImgMsg((MyImgHolder) holder, serviceTextBean);
                ((MyImgHolder) holder).senimg.setOnClickListener(v -> mItemClickListener.onItemClick(v,serviceTextBeanList1.get(position).getType(),position));
                break;
            case 4:
                setImgMsg((MyImgHolder) holder, serviceTextBean);
                ((MyImgHolder) holder).senimg.setOnClickListener(v -> mItemClickListener.onItemClick(v,serviceTextBeanList1.get(position).getType(),position));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if("admin".equals(serviceTextBeanList1.get(position).getSent_by())){
            if(serviceTextBeanList1.get(position).getType()==1){
                return 2;
            }else{
                return 4;
            }
        }else{
            if(serviceTextBeanList1.get(position).getType()==1){
                return 1;
            }else{
                return 3;
            }
        }
    }

    @Override
    public int getItemCount() {
        return serviceTextBeanList1 == null ? 0 : serviceTextBeanList1.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView sendtext;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.head_pic);
            sendtext = itemView.findViewById(R.id.send_text);
        }
    }

    private void setTextMsg(MyViewHolder holder, ChatBean.DataBean bean) {
        holder.sendtext.setText(bean.getContent());
        if("admin".equals(bean.getSent_by())){
            PicUtils.loadAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543656909175&di=a9952f754166f220e241835a0c8b5fed&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F58ee3d6d55fbb2fbd0b9f507444a20a44623dc11.jpg", holder.pic);
        }else{
            PicUtils.loadAvatar(PicUtils.getUrl(picurl), holder.pic);
        }
    }

    class MyImgHolder extends RecyclerView.ViewHolder {

        private ImageView head_pic, senimg;

        public MyImgHolder(View itemView) {
            super(itemView);
            head_pic = itemView.findViewById(R.id.head_pic);
            senimg = itemView.findViewById(R.id.send_img);
        }
    }

    private void setImgMsg(MyImgHolder holder, ChatBean.DataBean bean) {
        if("admin".equals(bean.getSent_by())){
            PicUtils.loadAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543656909175&di=a9952f754166f220e241835a0c8b5fed&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F58ee3d6d55fbb2fbd0b9f507444a20a44623dc11.jpg", holder.head_pic);
        }else{
            PicUtils.loadAvatar(PicUtils.getUrl(picurl), holder.head_pic);
        }
        if(bean.getContent().substring(0,1).equals("/")){
            Glide.with(context).load(bean.getContent()).into(holder.senimg);
        }else{
            Glide.with(context).load(PicUtils.getUrl(bean.getContent())).into(holder.senimg);
        }

    }

    private OnItemClickListener mItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(View view,int type,int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
