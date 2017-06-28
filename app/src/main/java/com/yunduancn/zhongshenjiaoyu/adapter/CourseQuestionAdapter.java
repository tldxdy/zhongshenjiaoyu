package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadShowListMdoel;

import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CourseQuestionAdapter extends BaseAdapter {

    Context context;
    List<ThreadShowListMdoel> list;
    LayoutInflater inflater;

    ThreadShowListMdoel threadShowListMdoel;

    public CourseQuestionAdapter(Context context, List<ThreadShowListMdoel> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.course_question_item, null);
            viewHolder.user_image = (ImageView) convertView.findViewById(R.id.user_image);
            viewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            viewHolder.text_introduction = (TextView) convertView.findViewById(R.id.text_introduction);
            viewHolder.image_is_gz = (TextView) convertView.findViewById(R.id.image_is_gz);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        threadShowListMdoel = list.get(i);

        Glide.with(context)
                .load(threadShowListMdoel.getLargeAvatar())
                .placeholder(R.mipmap.me)
                .error(R.mipmap.me)
                .into(viewHolder.user_image);
        viewHolder.user_name.setText(threadShowListMdoel.getNickname());

        viewHolder.text_time.setText(threadShowListMdoel.getCreatedTime() + " |  第" + (i + 1) + "楼");

        viewHolder.text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        viewHolder.text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        viewHolder.text_introduction.setText(Html.fromHtml(threadShowListMdoel.getContent(), imgGetter, null));

        viewHolder.image_is_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
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

    class ViewHolder {
        TextView user_name, text_time, text_introduction, image_is_gz;
        ImageView user_image;
    }
}
