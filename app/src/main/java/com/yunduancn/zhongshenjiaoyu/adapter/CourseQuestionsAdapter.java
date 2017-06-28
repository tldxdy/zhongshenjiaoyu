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

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.adapter.BaseAdapter;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadShowListMdoel;

import java.net.URL;

/**
 * Created by lq on 16/6/29.
 */
public class CourseQuestionsAdapter extends BaseAdapter<ThreadShowListMdoel, CourseQuestionsAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public CourseQuestionsAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.course_question_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int position) {
        ThreadShowListMdoel threadShowListMdoel = getItemData(position);
        Glide.with(mContext)
                .load(threadShowListMdoel.getLargeAvatar())
                .placeholder(R.mipmap.me)
                .error(R.mipmap.me)
                .into(viewHolder.user_image);
        viewHolder.user_name.setText(threadShowListMdoel.getNickname());

        viewHolder.text_time.setText(threadShowListMdoel.getCreatedTime() + " |  第" + (position + 1) + "楼");

        viewHolder.text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        viewHolder.text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        viewHolder.text_introduction.setText(Html.fromHtml(threadShowListMdoel.getContent(), imgGetter, null));

        viewHolder.image_is_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        private final TextView text_introduction;
        private final TextView image_is_gz;
        private final ImageView user_image;

        public ItemViewHolder(View itemView) {
            super(itemView);
            user_image = (ImageView) itemView.findViewById(R.id.user_image);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            text_time = (TextView) itemView.findViewById(R.id.text_time);
            text_introduction = (TextView) itemView.findViewById(R.id.text_introduction);
            image_is_gz = (TextView) itemView.findViewById(R.id.image_is_gz);
        }
    }

}
