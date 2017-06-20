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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.UserIndexModel;
import com.yunduancn.zhongshenjiaoyu.model.UserInfoModel;
import com.yunduancn.zhongshenjiaoyu.utils.ActivityCollector;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.DataCleanManager;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.dialogexit;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private ImageView back_image, user_image;
    TextView title, cache_num;

    private RelativeLayout cache_layout, user_information_layout;

    private SwitchCompat switchcompat;

    private TextView exit_login, user_name;


    private UserInfoModel userInfoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActivityCollector.addActivity(this);
        initView();


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

        user_image = (ImageView) findViewById(R.id.user_image);
        user_name = (TextView) findViewById(R.id.user_name);



        exit_login = (TextView) findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);

        switchcompat = (SwitchCompat) findViewById(R.id.switchcompat);
        switchcompat.setChecked(SharedPreferencesUtils.getValue(this,Constant.AppName,"isAutomaticPlay",true));
        switchcompat.setOnCheckedChangeListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        try {
            cache_num.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> map = new HashMap<>();
        map.put("userId",SharedPreferencesUtils.getValue(this,Constant.AppName,"userId",null));

        OkHttp_Utils.PostMethods(map, UrlUtils.userinfourl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {

            }

            @Override
            public void onMyResponse(String response, int id) {
                L.e("userinfourl",response.toString());
                Log.e("返回成功",response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONObject items = obj.getJSONObject("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserInfoModel>() {}.getType();
                        userInfoModel = gson.fromJson(items.toString(),type);

                            Picasso.with(getApplicationContext())
                                    .load(userInfoModel.getLargeAvatar())
                                    .error(R.mipmap.me)
                                    .resize(100,100)
                                    .placeholder(R.mipmap.me)
                                    .into(user_image);



                        user_name.setText(userInfoModel.getNickname());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;
            case R.id.user_information_layout:

                if(userInfoModel == null){
                    return;
                }

                intent.setClass(this,PersonalCenterActivity.class);
                intent.putExtra("userInfoModel",userInfoModel);
                startActivity(intent);

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

                break;

            case R.id.exit_login:


                dialogexit.show(this, "是否退出登录？", new dialogexit.onexitlistener() {
                    @Override
                    public void exitlistener() {
                        SharedPreferencesUtils.putValue(getApplicationContext(), Constant.AppName,"userId",null);
                        ActivityCollector.finishAll();
                        intent.setClass(SettingsActivity.this,LoginActivity.class);
                        ToastUtils.show(getApplicationContext(),"已退出登录");
                        startActivity(intent);
                        finish();
                    }
                });
                break;



            default:


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchcompat:
                SharedPreferencesUtils.putValue(getApplicationContext(), Constant.AppName,"isAutomaticPlay",b);

                break;

            default:

                break;
        }
    }
}
