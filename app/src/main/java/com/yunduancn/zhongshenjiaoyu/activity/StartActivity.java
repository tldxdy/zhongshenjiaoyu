package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunduancn.zhongshenjiaoyu.MainActivity;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        new Handler().postDelayed(new Runnable(){

            public void run() {

                initdate();
            }

        }, 1000);



    }

    private void initdate() {
        if(!SharedPreferencesUtils.getValue(getApplicationContext(), Constant.AppName,"isguide",false)){
            Intent intent = new Intent();
            intent.setClass(this, GuideActivity.class);
            startActivity(intent);
            SharedPreferencesUtils.putValue(getApplicationContext(),Constant.AppName,"isguide",true);

        }else {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

}
