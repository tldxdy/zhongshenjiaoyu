package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;


public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    protected Context mContext;
    protected int mLayout;
    protected List<T> mDatas;

    public MyRecyclerViewAdapter(Context mContext, int mLayout, List<T> mDatas) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mDatas = mDatas;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, int position) {
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.getItemView(), layoutPosition);
                }
            }
        });
        onBindView(holder, position);
    }

    public abstract void onBindView(MyRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void addData(int pos, T t) {
        mDatas.add(pos, t);
//        notifyDataSetChanged();
        notifyItemInserted(pos);
    }

    public void addData(T t) {
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void removeData(int pos) {
        mDatas.remove(pos);
//        notifyDataSetChanged();
        notifyItemRemoved(pos);
    }

    public void removeData(T t) {
        mDatas.removeAll((Collection<?>) t);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
