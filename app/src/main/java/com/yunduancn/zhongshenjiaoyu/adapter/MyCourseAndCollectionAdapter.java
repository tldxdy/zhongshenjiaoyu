package com.yunduancn.zhongshenjiaoyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.adapter.BaseAdapter;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.MyCourseAndCollectionModel;

/**
 * Created by lq on 16/6/29.
 */
public class MyCourseAndCollectionAdapter extends BaseAdapter<MyCourseAndCollectionModel, MyCourseAndCollectionAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public MyCourseAndCollectionAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.my_course_and_collection_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        MyCourseAndCollectionModel data = getItemData(position);
        if (data != null) {
            Glide.with(mContext)
                    .load(data.getPic())
                    .placeholder(R.mipmap.news)
                    .error(R.mipmap.news)
                    .into(holder.course_img);
            holder.course_name.setText(data.getCoursename());

            holder.notes_num.setText(data.getNotesnum() +"");
            holder.problem_num.setText(data.getThreadnum() +"");

        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView course_name;
        private final ImageView course_img;
        private final TextView notes_num;
        private final TextView problem_num;
        private final TextView learning_text;

        public ItemViewHolder(View itemView) {
            super(itemView);

            course_name = (TextView) itemView.findViewById(R.id.course_name);
            notes_num = (TextView) itemView.findViewById(R.id.notes_num);
            learning_text = (TextView) itemView.findViewById(R.id.learning_text);
            problem_num = (TextView) itemView.findViewById(R.id.problem_num);
            course_img = (ImageView) itemView.findViewById(R.id.course_img);
        }
    }

}
