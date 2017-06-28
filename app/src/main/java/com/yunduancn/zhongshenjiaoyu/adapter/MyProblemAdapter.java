package com.yunduancn.zhongshenjiaoyu.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.adapter.BaseAdapter;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.ThreadListModel;

import java.net.URL;

/**
 * Created by lq on 16/6/29.
 */
public class MyProblemAdapter extends BaseAdapter<ThreadListModel, MyProblemAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public MyProblemAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.course_problem_fragment_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        ThreadListModel data = getItemData(position);
        if (data != null) {
            /*Picasso.with(mContext)
                    .load(data.getLargeAvatar())
                    .placeholder(R.mipmap.me)
                    .error(R.mipmap.me)
                    .into(holder.user_image);
            holder.user_name.setText(data.getNickname());

            holder.text_time.setText(data.getCreatedTime());*/
            holder.user_image.setVisibility(View.GONE);
            holder.user_name.setVisibility(View.GONE);
            holder.text_time.setVisibility(View.GONE);

            holder.text_title.setText(data.getTitle());
            holder.text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
            holder.text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
            holder.text_introduction.setText(Html.fromHtml(data.getContent(), imgGetter, null));
        }
        
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Log.i("RG", "source---?>>>" + source);
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                Log.i("RG", "url---?>>>" + url);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            Log.i("RG", "url---?>>>" + url);
            return drawable;
        }
    };


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView user_name;
        private final TextView text_time;
        private final ImageView user_image;
        private final ImageView image_is_dz;
        private final TextView text_introduction;

        private final TextView text_num;

        private final TextView text_title;



        public ItemViewHolder(View itemView) {
            super(itemView);

            text_time = (TextView) itemView.findViewById(R.id.text_time);
            text_introduction = (TextView) itemView.findViewById(R.id.text_introduction);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_image = (ImageView) itemView.findViewById(R.id.user_image);

            text_num = (TextView) itemView.findViewById(R.id.text_num);
            image_is_dz = (ImageView) itemView.findViewById(R.id.image_is_dz);
            text_title = (TextView) itemView.findViewById(R.id.text_title);

        }
    }

}
