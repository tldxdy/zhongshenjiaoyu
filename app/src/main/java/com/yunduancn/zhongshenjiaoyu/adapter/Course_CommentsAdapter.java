package com.yunduancn.zhongshenjiaoyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.adapter.BaseAdapter;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.ReviewsListModel;
import com.yunduancn.zhongshenjiaoyu.view.CustonRatingbar;

/**
 * Created by lq on 16/6/29.
 */
public class Course_CommentsAdapter extends BaseAdapter<ReviewsListModel, Course_CommentsAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public Course_CommentsAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.course_comments_fragment_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        ReviewsListModel data = getItemData(position);
        if (data != null) {
            Picasso.with(mContext)
                    .load(data.getLargeAvatar())
                    .placeholder(R.mipmap.me)
                    .error(R.mipmap.me)
                    .into(holder.user_image);
            holder.user_name.setText(data.getNickname());

            holder.text_time.setText(data.getCreatedTime());
            holder.text_introduction.setText(data.getContent());
            holder.rateingbar.setmLikeCount(5, Integer.parseInt(data.getRating()));
        }
        
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView user_name;
        private final TextView text_time;
        private final ImageView user_image;
        private final ImageView image_is_dz;
        private final TextView text_introduction;

        private final TextView text_num;

        private final CustonRatingbar rateingbar;

        public ItemViewHolder(View itemView) {
            super(itemView);

            text_time = (TextView) itemView.findViewById(R.id.text_time);
            text_introduction = (TextView) itemView.findViewById(R.id.text_introduction);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_image = (ImageView) itemView.findViewById(R.id.user_image);

            text_num = (TextView) itemView.findViewById(R.id.text_num);
            image_is_dz = (ImageView) itemView.findViewById(R.id.image_is_dz);

            rateingbar = (CustonRatingbar) itemView.findViewById(R.id.rateingbar);
        }
    }

}
