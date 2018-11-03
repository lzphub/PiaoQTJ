package cn.dankal.basiclib.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.R;


public class ImageRvAdapter extends RecyclerView.Adapter<ImageRvAdapter.MyViewHolder> {

    private Context context;
    private List<Uri> uriList=new ArrayList<>();

    public ImageRvAdapter(Context context, List<Uri> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    public void UpData(List<Uri> uriList){
        this.uriList = uriList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(uriList.get(position)).into(holder.be_add_img);
        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickListener.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public  interface OnClickListener {
        void OnClick(int pos);
    }
    private OnClickListener ClickListener;

    public void setOnClickListener(OnClickListener clickListener){
        this.ClickListener=clickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView be_add_img,delete_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            be_add_img=itemView.findViewById(R.id.be_add_image);
            delete_img=itemView.findViewById(R.id.delete_image);
        }
    }
}
