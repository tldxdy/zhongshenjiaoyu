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
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CourseCategoryAdapter extends BaseAdapter {

    Context context;
    List<CourseCategoryModel> list;
    LayoutInflater inflater;

    CourseCategoryModel courseCategoryModel;

    public CourseCategoryAdapter(Context context, List<CourseCategoryModel> list){
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
            convertView = inflater.inflate(R.layout.course_category_item, null);
            viewHolder.course_img = (ImageView) convertView.findViewById(R.id.course_img);
            viewHolder.course_name = (TextView) convertView.findViewById(R.id.course_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        courseCategoryModel = list.get(i);

        Glide.with(context)
                .load(courseCategoryModel.getPic())
                .placeholder(R.color.cl_default)
                .error(R.color.white)
                .into(viewHolder.course_img);
        viewHolder.course_name.setText(courseCategoryModel.getClassname());



        return convertView;
    }


    class ViewHolder {
        TextView course_name;
        ImageView course_img;
    }
}