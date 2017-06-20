package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.MyNotesDetailsAdapter;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesDetailsListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesDetailsModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyNotesDetailsActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title;
    private ImageView back_image;
    private MyNotesListModel myNotesListModel;


    private TextView course_name;
    private ImageView course_img;
    private TextView notes_num;
    private TextView course_time;
    ListView listView;


    MyNotesDetailsModel myNotesDetailsModel;
    List<MyNotesDetailsListModel> list;

    MyNotesDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes_details);

        initView();
        Dialogmanager.loadstart(this);
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("courseId", myNotesListModel.getCourseId() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.mynotesshowurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("response.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<MyNotesDetailsModel>() {
                        }.getType();
                        myNotesDetailsModel = gson.fromJson(obj.toString(),type);
                        list.clear();
                        list.addAll(myNotesDetailsModel.getItems());
                        adapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void initView() {
        myNotesListModel = (MyNotesListModel) getIntent().getSerializableExtra("myNotesListModel");

        title = (TextView) findViewById(R.id.title);
        title.setText("我的手记");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        list = new ArrayList<>();
        myNotesDetailsModel = new MyNotesDetailsModel();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new MyNotesDetailsAdapter(this,list);
        listView.setAdapter(adapter);


        course_name = (TextView) findViewById(R.id.course_name);
        notes_num = (TextView) findViewById(R.id.notes_num);
        course_time = (TextView) findViewById(R.id.course_time);
        course_img = (ImageView) findViewById(R.id.course_img);

            Glide.with(this)
                    .load(myNotesListModel.getPic())
                    .placeholder(R.mipmap.news)
                    .error(R.mipmap.news)
                    .into(course_img);
            course_name.setText(myNotesListModel.getCoursename());

            notes_num.setText("共" + myNotesListModel.getNoteNum() +"篇笔记");
            course_time.setText(myNotesListModel.getNoteLastUpdateTime() +"");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            default:

                break;
        }
    }
}
