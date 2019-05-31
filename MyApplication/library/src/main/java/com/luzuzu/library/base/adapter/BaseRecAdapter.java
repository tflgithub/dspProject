package com.luzuzu.library.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fula on 2019/5/14.
 */

public abstract class BaseRecAdapter <T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    private List<T> list;

    public onItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public onItemLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setItemLongClickListener(onItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    private onItemClickListener itemClickListener;
    private onItemLongClickListener itemLongClickListener;
    public Context context;


    public BaseRecAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        K holder = onCreateHolder();
        //绑定listener
        bindListener(holder);
        return holder;
    }


    /**
     * 绑定事件
     *
     * @param holder
     */
    private void bindListener(final K holder) {
        if (holder == null) {
            return;
        }
        View itemView = holder.itemView;
        if (itemView == null) {
            return;
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(params);
        if (getItemClickListener() != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItemClickListener().onItemClick(BaseRecAdapter.this, v, holder.getLayoutPosition());
                }
            });
        }

        if (getItemLongClickListener() != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return getItemLongClickListener().onItemLongClick(BaseRecAdapter.this, v, holder.getLayoutPosition());
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        onHolder(holder, list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 通过资源res获得view
     *
     * @param res
     * @return
     */
    public View getViewByRes(int res) {
        return LayoutInflater.from(context).inflate(res, null);
    }

    /**
     * 填充数据
     *
     * @param holder
     * @param position
     */
    public abstract void onHolder(K holder, T bean, int position);


    public abstract K onCreateHolder();

    public interface onItemClickListener {
        void onItemClick(BaseRecAdapter adapter, View view, int position);
    }

    public interface onItemLongClickListener {
        boolean onItemLongClick(BaseRecAdapter adapter, View view, int position);
    }

    public void setData(List<T> lt) {
        if (list == null) {
            list = new ArrayList<T>();
        }

        if (lt == null) {
            lt = new ArrayList<T>();
        }
        list = lt;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return list;
    }
}
