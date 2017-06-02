package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.view.MyScrollView;

public class CourseInformationActivity extends AppCompatActivity implements MyScrollView.ScrollViewListener, View.OnClickListener {


    MyScrollView scrollView;

    RelativeLayout tob_layout;

    TextView text_name, title;

    ImageView image_collection, back_image;
    Drawable drawable;

    TextView text_study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);


        initView();
    }
    float y = 0;
    private void initView() {
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

        image_collection = (ImageView) findViewById(R.id.image_collection);



        drawable = getResources().getDrawable(R.mipmap.ic_collection);

        tob_layout.getBackground().setAlpha(0);

        scrollView.setScrollViewListener(this);

    }



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
                startActivity(new Intent(this,VideoActivity.class));
                break;

            case R.id.back_image:

                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawable = null;
    }
}
