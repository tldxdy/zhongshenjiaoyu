package com.yunduancn.zhongshenjiaoyu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.wakehao.bar.BottomNavigationBar;
import com.wakehao.bar.BottomNavigationItemWithDot;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.fragment.CourseFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.HomeFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.MyFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.NewsFragment;
import com.yunduancn.zhongshenjiaoyu.utils.ActivityCollector;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationBar bar;
    private ViewPager viewPager;
    private WePagerAdapter mAdapter;
    List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);

       //CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
        bar = (BottomNavigationBar) findViewById(R.id.bar);



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CourseFragment());
        fragmentList.add(new NewsFragment());
        fragmentList.add(new MyFragment());
        mAdapter = new WePagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        //设置页面缓存个数
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //mAdapter.getItem(position).onResume();
                bar.setItemSelected(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bar.setOnNavigationItemSelectedListener(new BottomNavigationBar.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull BottomNavigationItemWithDot item, int selectedPosition) {
                viewPager.setCurrentItem(selectedPosition);
                return true;
            }

            @Override
            public void onNavigationItemSelectedAgain(@NonNull BottomNavigationItemWithDot item, int reSelectedPosition) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        //CretinAutoUpdateUtils.getInstance(this).destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK||requestCode!=1)return;
        //用户切换item
        bar.setItemSelected(3,false);



        super.onActivityResult(requestCode, resultCode, data);

    }




    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.show(getApplicationContext(),"再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
