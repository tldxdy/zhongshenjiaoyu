package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.fragment.CourseCenterFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.CrowdFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.DisciplineCenterFragment;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coursectivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private ImageView back_image;


    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist;
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("人群", "课程中心", "学科中心");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursectivity);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("课程");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);


        flist = new ArrayList<Fragment>();
        flist.add(new CrowdFragment());

        flist.add(new CourseCenterFragment());
        flist.add(new DisciplineCenterFragment());
        vp = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mIndicator.setVisibaleTabCount(flist.size());
        mIndicator.setTabItemTitles(mTitles);

        mIndicator.setViewPager(vp, 0);

        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
        wePagerAdapter = new WePagerAdapter(getSupportFragmentManager(), flist);
        vp.setAdapter(wePagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:

                finish();
                break;
            default:
                break;
        }
    }
}
