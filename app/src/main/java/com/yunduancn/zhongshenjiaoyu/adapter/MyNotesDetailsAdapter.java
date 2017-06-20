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
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesDetailsListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesListModel;

import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class MyNotesDetailsAdapter extends BaseAdapter {


        Context context;
        List<MyNotesDetailsListModel> list;
        LayoutInflater inflater;

    MyNotesDetailsListModel myNotesDetailsListModel;

        public MyNotesDetailsAdapter(Context context, List<MyNotesDetailsListModel> list){
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
                convertView = inflater.inflate(R.layout.my_notes_details_item, null);
                viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                viewHolder.text_num = (TextView) convertView.findViewById(R.id.text_num);
                viewHolder.text_content = (TextView) convertView.findViewById(R.id.text_content);
                viewHolder.text_content.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                viewHolder.text_content.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            myNotesDetailsListModel = list.get(i);

            viewHolder.text_name.setText(myNotesDetailsListModel.getLessonname());
            viewHolder.text_num.setText("共" + myNotesDetailsListModel.getLength() + "字");

            viewHolder.text_content.setText(Html.fromHtml(myNotesDetailsListModel.getContent(), imgGetter, null));



            return convertView;
        }


        class ViewHolder {
            TextView text_name, text_num, text_content;
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
    }
