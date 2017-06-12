package com.yunduancn.zhongshenjiaoyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.adapter.BaseAdapter;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.CourseListActivity;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;

import java.util.Date;
import java.util.Random;

/**
 * Created by lq on 16/6/29.
 */
public class CourseListAdapter extends BaseAdapter<Coursesmodel, CourseListAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public CourseListAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.cardview_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        Coursesmodel data = getItemData(position);
        if (data != null) {
            Glide.with(mContext)
                    .load(data.getPic())
                    .placeholder(R.color.cl_default)
                    .error(R.color.white)
                    .into(holder.course_img);
            holder.course_name.setText(data.getCoursename());

            holder.watch_number.setText(data.getSeen() + "人看过");
        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView watch_number;
        private final TextView course_name;
        private final ImageView course_img;

        public ItemViewHolder(View itemView) {
            super(itemView);
             course_img = (ImageView) itemView.findViewById(R.id.course_img);
             course_name = (TextView) itemView.findViewById(R.id.course_name);
             watch_number = (TextView) itemView.findViewById(R.id.watch_number);
        }
    }

}
