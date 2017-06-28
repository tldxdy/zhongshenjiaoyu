package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.MyrecordModel;
import com.yunduancn.zhongshenjiaoyu.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HistoricalRecordChildAdapter extends BaseAdapter {


        Context context;
        LayoutInflater inflater;

        List<MyrecordModel> list;

        MyrecordModel model;

        public HistoricalRecordChildAdapter(Context context, List<MyrecordModel> list){
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
                convertView = inflater.inflate(R.layout.historical_record_child_item, null);
                viewHolder.text_name = (TextView) convertView.findViewById(R.id.text_name);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            model = list.get(i);
            viewHolder.text_name.setText(model.getCoursename());
            return convertView;
        }


        class ViewHolder {
            TextView text_name;
        }
    }
