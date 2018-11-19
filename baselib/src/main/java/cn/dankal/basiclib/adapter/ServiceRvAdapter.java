package cn.dankal.basiclib.adapter;

import android.content.Context;
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
import cn.dankal.basiclib.bean.ServiceTextBean;

public class ServiceRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ServiceTextBean> serviceTextBeanList=new ArrayList<>();
    private Context context;

    public ServiceRvAdapter(List<ServiceTextBean> serviceTextBeanList, Context context) {
        this.serviceTextBeanList = serviceTextBeanList;
        this.context = context;
    }

    public void update(List<ServiceTextBean> serviceTextBeanList){
        this.serviceTextBeanList=serviceTextBeanList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case 1:
                view=LayoutInflater.from(context).inflate(R.layout.service_text_rv_item,parent,false);
                viewHolder=new MyViewHolder(view);
                break;
            case 2:
                view=LayoutInflater.from(context).inflate(R.layout.service_customer_text_rv_item,parent,false);
                viewHolder=new MyViewHolder(view);
                break;
            case 3:
                view=LayoutInflater.from(context).inflate(R.layout.service_image_rv_item,parent,false);
                viewHolder=new MyImgHolder(view);
                break;
            case 4:
                view=LayoutInflater.from(context).inflate(R.layout.service_customer_image_rv_item,parent,false);
                viewHolder=new MyImgHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ServiceTextBean serviceTextBean=serviceTextBeanList.get(position);
            switch (holder.getItemViewType()){
                case 1:
                    setTextMsg((MyViewHolder) holder,serviceTextBean);
                    break;
                case 2:
                    setTextMsg((MyViewHolder) holder,serviceTextBean);
                    break;
                case 3:
                    setImgMsg((MyImgHolder) holder,serviceTextBean);
                    break;
                case 4:
                    setImgMsg((MyImgHolder) holder,serviceTextBean);
                    break;
            }
    }

    @Override
    public int getItemViewType(int position) {
        return serviceTextBeanList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return serviceTextBeanList==null?0 :serviceTextBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView sendtext;
        public MyViewHolder(View itemView) {
            super(itemView);
            pic=itemView.findViewById(R.id.head_pic);
            sendtext=itemView.findViewById(R.id.send_text);
        }
    }

    private void setTextMsg(MyViewHolder holder,ServiceTextBean bean){
        holder.sendtext.setText(bean.getSend_text());
    }

    class MyImgHolder extends RecyclerView.ViewHolder{

        private ImageView head_pic,senimg;
        public MyImgHolder(View itemView) {
            super(itemView);
            head_pic=itemView.findViewById(R.id.head_pic);
            senimg=itemView.findViewById(R.id.send_img);
        }
    }
    private void setImgMsg(MyImgHolder holder,ServiceTextBean bean){
//        Glide.with(context).load(bean.getSend_img()).into(holder.senimg);

      holder.senimg.setImageURI(bean.getSend_img());

//        File tempFile = new File(bean.getSend_img().getPath());
//        Uri uri = Uri.fromFile(tempFile);
//        holder.senimg.setImageURI(uri);
    }
}
