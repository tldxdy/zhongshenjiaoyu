package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CourseAdapter extends BaseAdapter {

    Context context;
    List<?> list;
    LayoutInflater inflater;

    Coursesmodel coursesmodel;

    public CourseAdapter(Context context, List<?> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return 8;
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
            convertView = inflater.inflate(R.layout.classification_item, null);
            viewHolder.course_img = (ImageView) convertView.findViewById(R.id.course_img);
            viewHolder.course_name = (TextView) convertView.findViewById(R.id.course_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


 /*       coursesmodel = list.get(i);

        Glide.with(context)
                .load(coursesmodel.getPic())
                .placeholder(R.color.cl_default)
                .error(R.color.white)
                .into(viewHolder.course_img);
        viewHolder.course_name.setText(coursesmodel.getCoursename());

        viewHolder.watch_number.setText(coursesmodel.getSeen() + "人看过");*/


        if(i == 7){

            viewHolder.course_name.setText("更多课程");
        }

        return convertView;
    }


    class ViewHolder {
        TextView course_name, watch_number;
        ImageView course_img;
    }
}
