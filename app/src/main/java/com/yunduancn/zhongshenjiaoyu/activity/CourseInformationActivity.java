package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.CourseListModel;
import com.yunduancn.zhongshenjiaoyu.model.CourseshowModel;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.MyScrollView;
import com.yunduancn.zhongshenjiaoyu.view.dialogexit;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class CourseInformationActivity extends AppCompatActivity implements MyScrollView.ScrollViewListener, View.OnClickListener {


    MyScrollView scrollView;

    RelativeLayout tob_layout;

    TextView text_name, title, text_introduce;

    ImageView image_collection, back_image;
    Drawable drawable;

    TextView text_study;
    Coursesmodel coursesmodel;

    String userId;

    CourseshowModel courseshowModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);


        initView();
        Dialogmanager.loadstart(this);
        initData();
    }


    float y = 0;
    private void initView() {
        coursesmodel = (Coursesmodel) getIntent().getSerializableExtra("coursesmodel");
        userId = SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null);
        courseshowModel = new CourseshowModel();

        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        tob_layout = (RelativeLayout) findViewById(R.id.tob_layout);

        text_study = (TextView) findViewById(R.id.text_study);
        text_study.setOnClickListener(this);
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);

        text_name = (TextView) findViewById(R.id.text_name);
        title = (TextView) findViewById(R.id.title);
        text_name.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTextColor(Color.argb(0, 255, 255, 255));

        text_name.setText(coursesmodel.getCoursename());
        title.setText(coursesmodel.getCoursename());

        image_collection = (ImageView) findViewById(R.id.image_collection);
        image_collection.setOnClickListener(this);


        drawable = getResources().getDrawable(R.mipmap.ic_collection);

        tob_layout.getBackground().setAlpha(0);

        scrollView.setScrollViewListener(this);



        text_introduce = (TextView) findViewById(R.id.text_introduce);

    }


    private void initData() {

            Map<String, String> map = new HashMap<>();
            map.put("courseId",coursesmodel.getId());
            map.put("userid",  userId);

        Log.e("map.toString()", map.toString());
            OkHttp_Utils.PostMethods(map, UrlUtils.courseshowurl, new OkHttp_Utils.CallBack() {
                @Override
                public void onMyError(Call call, Exception e, int id) {
                    Dialogmanager.loadfinsh(0);
                }

                @Override
                public void onMyResponse(String response, int id) {
                    Dialogmanager.loadfinsh(0);
                    try {
                        JSONObject json = new JSONObject(response);
                        int code = json.getInt("code");
                        if (code == 0) {
                            JSONObject obj = json.getJSONObject("obj");
                            Gson gson = new Gson();
                            Type type = new TypeToken<CourseshowModel>() {
                            }.getType();
                            courseshowModel = gson.fromJson(obj.toString(),type);
                            initFill();

                        } else {
                            ToastUtils.show(getApplicationContext(), json.getString("msg"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            });

    }

    private void initlearning() {

        Map<String, String> map = new HashMap<>();
        map.put("courseId",coursesmodel.getId());
        map.put("userid",  userId);
        OkHttp_Utils.PostMethods(map, UrlUtils.joincourseurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        /*JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<CourseshowModel>() {
                        }.getType();
                        courseshowModel = gson.fromJson(obj.toString(),type);
                        initFill();*/

                        initData();

                    } else {
                        Dialogmanager.loadfinsh(0);
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Dialogmanager.loadfinsh(0);
                }

            }

        });

    }


    private void initFill() {

        text_introduce.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        text_introduce.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        text_introduce.setText(Html.fromHtml(courseshowModel.getIntroduce(), imgGetter, null));
        if(!courseshowModel.isUserIsStudent()){
            text_study.setText("开始学习");
        }else{
            text_study.setText("继续学习");
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


    private void initweizhi() {
        if(scrollView.getScrollY() <= 0){
            text_name.setTextColor(Color.argb(255, 255, 255, 255));
            title.setTextColor(Color.argb(0, 255, 255, 255));
            drawable.mutate().setAlpha(255);
            tob_layout.getBackground().setAlpha(0);
        }else if(scrollView.getScrollY() < 400){

            tob_layout.getBackground().setAlpha((int)(scrollView.getScrollY()/400.0 * 255));

            text_name.setTextColor(Color.argb(255 - (int)(scrollView.getScrollY()/400.0 * 255), 255, 255, 255));
            title.setTextColor(Color.argb((int)(scrollView.getScrollY()/400.0 * 255), 255, 255, 255));
            drawable.mutate().setAlpha(255 - (int)(scrollView.getScrollY()/400.0 * 255));

        }else{
            title.setTextColor(Color.argb(255, 255, 255, 255));
            text_name.setTextColor(Color.argb(0, 255, 255, 255));
            tob_layout.getBackground().setAlpha(255);
            drawable.mutate().setAlpha(0);
        }
        image_collection.setImageDrawable(drawable);
    }


    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        initweizhi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_study:
                if(!courseshowModel.isUserIsStudent()){
                    dialogexit.show(this, "是否加入学习？", new dialogexit.onexitlistener() {
                        @Override
                        public void exitlistener() {
                            Dialogmanager.loadstart(CourseInformationActivity.this);
                            initlearning();
                        }
                    });
                }else{
                    Intent intent = new Intent(this, VideoActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.back_image:

                finish();
                break;

            case R.id.image_collection:

                ToastUtils.show(getApplicationContext(),"点击收藏");
                break;

            default:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawable = null;
    }
}
