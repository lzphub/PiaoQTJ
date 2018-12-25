package cn.dankal.basiclib.base.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {

    private static final String TAG = "BaseRecyclerViewAdapter";
    protected Context context;
    protected List<T> datas = new ArrayList<>();

    protected LayoutInflater mInflater;

    private OnBaseRvItemClickListener<T, BaseRecyclerViewHolder<T>> rvitemClickListener;
    private OnBaseRvItemLongClickListener<T, BaseRecyclerViewHolder<T>> rvitemLongClickListener;

    public BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(@NonNull Context context, @NonNull List<T> datas) {
        this.context = context;
        this.datas = new ArrayList<>();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (!datas.isEmpty()) {
            return getViewType(position, datas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = null;
        if (context == null) {
            context = parent.getContext();
            mInflater = LayoutInflater.from(context);
        }

        if (getLayoutResId(viewType) != 0) {
            View rootView = mInflater.inflate(getLayoutResId(viewType), parent, false);
            holder = getViewHolder(parent, rootView, viewType);
        } else {
            holder = getViewHolder(parent, null, viewType);
        }
        setUpItemEvent(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (!datas.isEmpty()){
            T data = datas.get(position);
//        holder.itemView.setTag(R.id.recycler_view_tag, data);
            holder.onBindData(data, position);
        }

    }

    protected void setUpItemEvent(final BaseRecyclerViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvitemClickListener != null) {
                    //这个获取位置的方法，防止添加删除导致位置不变
                    int layoutPosition = holder.getAdapterPosition();
                    try {
                        rvitemClickListener.onItemClick(holder, holder.itemView, layoutPosition, datas
                                .get(layoutPosition));
                    } catch (ClassCastException e) {
                        rvitemClickListener.onItemClick(holder.itemView, layoutPosition, datas
                                .get(layoutPosition));
                    }
                }
            }
        });

        //longclick
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (rvitemLongClickListener != null) {
                    int layoutPosition = holder.getAdapterPosition();
                    try {
                        return rvitemLongClickListener.onItemLongClick(holder, holder.itemView, layoutPosition, datas
                                .get(layoutPosition));
                    } catch (ClassCastException e) {
                        return rvitemLongClickListener.onItemLongClick(holder.itemView, layoutPosition, datas
                                .get(layoutPosition));
                    }
                } else {
                    return true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public boolean isEmpty() {
        return datas == null || datas.size() == 0;
    }

    public void updateData(List<T> datas) {
        if (datas != null) {
            this.datas.clear();
            this.datas.addAll(datas);
        } else {
            this.datas.clear();
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        if (this.datas != null) {
            this.datas.clear();
            notifyDataSetChanged();
        }
    }

    public void addMore(List<T> datas) {
        if (!(datas == null || datas.size() <= 0)) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void remove(T data) {
        if (data != null) {
            int pos = this.datas.indexOf(data);
            this.datas.remove(data);
            if (pos != -1) {
                notifyItemRemoved(pos);
            } else {
                notifyDataSetChanged();
            }
        }
    }


    public void remove(int position) {
        if (position != -1) {
            try {
                this.datas.remove(position);
                notifyItemRemoved(position);
            } catch (Exception e) {
                e.printStackTrace();
                notifyDataSetChanged();
            }
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    public void addData(int pos, @NonNull T data) {
        if (datas != null) {
            datas.add(pos, data);
            notifyItemInserted(pos);
        }
    }

    public void addData(@NonNull T data) {
        if (datas != null) {
            datas.add(data);
            notifyItemInserted(datas.size() - 1);
        }
    }

    public void deleteData(int pos) {
        if (datas != null && datas.size() > pos) {
            datas.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public T findData(int pos) {
        if (pos < 0 || pos > datas.size()) {
            Log.e(TAG, "这个position他喵咪的太强大了，我hold不住");
            return null;
        }
        return datas.get(pos);
    }

    protected int getViewType(int position, @NonNull T data) {
        return 0;
    }

    protected abstract int getLayoutResId(int viewType);

    protected abstract BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType);

    public OnBaseRvItemClickListener getRvitemClickListener() {
        return rvitemClickListener;
    }

    public void setRvitemClickListener(OnBaseRvItemClickListener rvitemClickListener) {
        this.rvitemClickListener = rvitemClickListener;
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<T> onRvItemClickListener) {
        this.rvitemClickListener = onRvItemClickListener;
    }

    public OnBaseRvItemLongClickListener getRvitemLongClickListener() {
        return rvitemLongClickListener;
    }

    public void setRvitemLongClickListener(OnBaseRvItemLongClickListener rvitemLongClickListener) {
        this.rvitemLongClickListener = rvitemLongClickListener;
    }

    public void setOnRvLongItemClickListener(OnRvItemLongClickListener<T> onRvItemLongClickListener) {
        this.rvitemLongClickListener = onRvItemLongClickListener;
    }

}