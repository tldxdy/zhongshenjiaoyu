package com.yunduancn.zhongshenjiaoyu.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.CourseInformationActivity;
import com.yunduancn.zhongshenjiaoyu.activity.VideoActivity;
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;
import com.yunduancn.zhongshenjiaoyu.model.CoursePlayModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyrecordModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HistoricalRecordAdapter extends BaseAdapter {


        Context context;
        LayoutInflater inflater;

        List<String> listString;
        List<ArrayList<MyrecordModel>> list;

        public HistoricalRecordAdapter(Context context, List<ArrayList<MyrecordModel>> list, List<String> listString){
            this.context = context;
            this.list = list;
            this.listString = listString;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return listString.size();
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
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.historical_record_item, null);
                viewHolder.listView = (MyListView) convertView.findViewById(R.id.listView);
                viewHolder.time = (TextView) convertView.findViewById(R.id.time);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            HistoricalRecordChildAdapter adapter = new HistoricalRecordChildAdapter(context,list.get(i));

            viewHolder.listView.setAdapter(adapter);

            viewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                    Dialogmanager.loadstart(context);
                    initstudying(list.get(i).get(j));
                }
            });


            viewHolder.time.setText(listString.get(i));


            return convertView;
        }


        class ViewHolder {
            TextView time;
            MyListView listView;
        }

    private void initstudying(final MyrecordModel model) {

        Map<String, String> map = new HashMap<>();
        map.put("courseId",model.getCourseId());
        map.put("lessonId",model.getLessonId());
        map.put("userid",  SharedPreferencesUtils.getValue(context, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.courseplayurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Log.e("courseplayurl",response.toString());
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<CoursePlayModel>() {
                        }.getType();
                        CoursePlayModel coursePlayModel = gson.fromJson(obj.toString(),type);
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("coursePlayModel",coursePlayModel);
                        intent.putExtra("courseId",model.getCourseId());
                        intent.putExtra("lessonId",model.getLessonId());
                        context.startActivity(intent);
                    } else {
                        ToastUtils.show(context.getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }
    }
