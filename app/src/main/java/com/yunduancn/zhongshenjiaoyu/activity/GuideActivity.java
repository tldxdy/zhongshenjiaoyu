package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunduancn.zhongshenjiaoyu.GuidePage.PageFrameLayout;
import com.yunduancn.zhongshenjiaoyu.R;

public class GuideActivity extends AppCompatActivity {


    PageFrameLayout contentFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }


        contentFrameLayout = (PageFrameLayout) findViewById(R.id.contentFrameLayout);
        // 设置资源文件和选中圆点
        contentFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab3
        },R.mipmap.banner_on,R.mipmap.banner_off);
    }

}
