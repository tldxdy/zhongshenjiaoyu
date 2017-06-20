package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.CourseChapterModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CoursePlayingItemAdapter extends BaseAdapter {


        Context context;
        List<CourseChapterModel> list;
        LayoutInflater inflater;

        CourseChapterModel courseChapterModel;
        int i = 0;

        public CoursePlayingItemAdapter(Context context, List<CourseChapterModel> list,int i){
            this.context = context;
            this.list = list;
            this.i = i;
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


        public void setI(int i){
            this.i = i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.playing_item, null);
                viewHolder.text_num = (TextView) convertView.findViewById(R.id.text_num);
                viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);
                viewHolder.text_time = (TextView) convertView.findViewById(R.id.text_time);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            courseChapterModel = list.get(i);
            viewHolder.text_num.setText("第" + (1+i) + "章：");
            viewHolder.text_name.setText(courseChapterModel.getTitle());
            viewHolder.text_time.setText(courseChapterModel.getLength());

            if(this.i == i){
                viewHolder.text_num.setTextColor(0xff129955);
                viewHolder.text_name.setTextColor(0xff129955);
                viewHolder.text_time.setTextColor(0xff129955);

            }else{
                viewHolder.text_num.setTextColor(0xff989898);
                viewHolder.text_name.setTextColor(0xff989898);
                viewHolder.text_time.setTextColor(0xff989898);
            }

            return convertView;
        }


        class ViewHolder {
            TextView text_num, text_name, text_time;
        }
    }
