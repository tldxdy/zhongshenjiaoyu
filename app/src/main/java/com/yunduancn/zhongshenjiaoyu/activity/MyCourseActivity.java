package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.fragment.MyCollectionFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.MyCourseFragment;
import com.yunduancn.zhongshenjiaoyu.model.UserIndexModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyCourseActivity extends AppCompatActivity implements View.OnClickListener{

    TextView title;

    ImageView back_image;

    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist;
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("最近学习", "我的收藏");
    private int CourseAndCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        Dialogmanager.loadstart(this);
        initView();
    }


    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("我的课程");
        CourseAndCollection = getIntent().getIntExtra("CourseAndCollection",0);

        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        flist = new ArrayList<Fragment>();
        flist.add(new MyCourseFragment());

        flist.add(new MyCollectionFragment());
        vp = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mIndicator.setVisibaleTabCount(flist.size());
        mIndicator.setTabItemTitles(mTitles);

        mIndicator.setViewPager(vp, CourseAndCollection);

        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
        wePagerAdapter = new WePagerAdapter(getSupportFragmentManager(), flist);
        vp.setAdapter(wePagerAdapter);
        vp.setCurrentItem(CourseAndCollection);

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
