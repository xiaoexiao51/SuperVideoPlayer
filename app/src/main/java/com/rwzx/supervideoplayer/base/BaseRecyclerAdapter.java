package com.rwzx.supervideoplayer.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MMM on 2016/8/8.
 * 打造通用适配器SuperRecyclerAdapter
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected final List<T> mItems;
    protected Context mContext;

    public BaseRecyclerAdapter(List<T> items) {
        mItems = (items != null) ? items : new ArrayList<T>();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        onBindViewHolder(holder, position, mItems.get(position));

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    abstract protected int getLayoutId(int viewType);

    abstract protected void onBindViewHolder(BaseViewHolder holder, int position, T item);

    public void add(int position, T item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(Collection<T> items) {
        int lastIndex = this.mItems.size();
        if (this.mItems.addAll(items)) {
            notifyItemRangeInserted(lastIndex, items.size());
        }
    }

    public void addAll2Header(Collection<T> list) {
        if (this.mItems.addAll(0, list)) {
            notifyItemRangeInserted(0, list.size());
        }
    }

    public void delete(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        if (position != (mItems.size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mItems.size() - position);
        }
    }

    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }
}
