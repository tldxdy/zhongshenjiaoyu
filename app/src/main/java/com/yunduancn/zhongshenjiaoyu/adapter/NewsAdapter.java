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
import com.yunduancn.zhongshenjiaoyu.model.NewsModel;

/**
 * Created by lq on 16/6/29.
 */
public class NewsAdapter extends BaseAdapter<NewsModel, NewsAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public NewsAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        NewsModel data = getItemData(position);
        if (data != null) {
            Glide.with(mContext)
                    .load(data.getThumb())
                    .placeholder(R.mipmap.news)
                    .error(R.mipmap.news)
                    .into(holder.image_news);
            holder.news_name.setText(data.getTitle());

            holder.news_introduction.setText(data.getJianxu() +"");
            holder.text_time.setText(data.getPublishedTime() +"");

        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView news_name;
        private final ImageView image_news;
        private final TextView news_introduction;
        private final TextView text_time;

        public ItemViewHolder(View itemView) {
            super(itemView);

            news_name = (TextView) itemView.findViewById(R.id.news_name);
            news_introduction = (TextView) itemView.findViewById(R.id.news_introduction);
            text_time = (TextView) itemView.findViewById(R.id.text_time);
            image_news = (ImageView) itemView.findViewById(R.id.image_news);
        }
    }

}
