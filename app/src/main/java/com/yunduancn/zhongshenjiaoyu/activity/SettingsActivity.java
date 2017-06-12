package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.DataCleanManager;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.view.dialogexit;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private ImageView back_image;
    TextView title, cache_num;

    private RelativeLayout cache_layout, user_information_layout;

    private SwitchCompat switchcompat;

    private TextView exit_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
        initData();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("设置");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        user_information_layout = (RelativeLayout) findViewById(R.id.user_information_layout);
        user_information_layout.setOnClickListener(this);



        cache_num = (TextView) findViewById(R.id.cache_num);
        cache_layout = (RelativeLayout) findViewById(R.id.cache_layout);
        cache_layout.setOnClickListener(this);





        exit_login = (TextView) findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);

        switchcompat = (SwitchCompat) findViewById(R.id.switchcompat);
        switchcompat.setOnCheckedChangeListener(this);



    }

    private void initData() {
        try {
            cache_num.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;
            case R.id.user_information_layout:

                intent.setClass(this,PersonalCenterActivity.class);

                startActivityForResult(intent,101);

                break;



            case R.id.cache_layout:

                dialogexit.show(this, "是否清理缓存数据？", new dialogexit.onexitlistener() {
                    @Override
                    public void exitlistener() {
                        try {
                            DataCleanManager.clearAllCache(getApplicationContext());
                            cache_num.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ToastUtils.show(getApplicationContext(),"已清理缓存");

                    }
                });



                /*new AlertDialog.Builder(this).setTitle("提示")//设置对话框标题

                        .setMessage("是否清理缓存数据？")//设置显示的内容

                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                            @Override

                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件



                            }

                        }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//响应事件

                        // TODO Auto-generated method stub

                        Log.i("alertdialog"," 请保存数据！");

                    }

                }).show();*/




                break;

            case R.id.exit_login:


                dialogexit.show(this, "是否退出登录？", new dialogexit.onexitlistener() {
                    @Override
                    public void exitlistener() {
                        SharedPreferencesUtils.putValue(getApplicationContext(), Constant.AppName,"userId",null);

                        ToastUtils.show(getApplicationContext(),"已退出登录");

                        finish();
                    }
                });
                break;



            default:


                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchcompat:
            ToastUtils.show(getApplicationContext(),b + "");
                break;

            default:

                break;
        }
    }
}
