package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseCategoryAdapter;
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;
import com.yunduancn.zhongshenjiaoyu.utils.CountDownTimerUtils;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    final int RESULT_CODE=101;
    ImageView image_back;


    TextView captcha, login_text;

    CountDownTimerUtils count;


    EditText user_name, captcha_edit, user_pass, user_passs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);

        captcha = (TextView) findViewById(R.id.captcha);
        captcha.setOnClickListener(this);
        login_text = (TextView) findViewById(R.id.login_text);
        login_text.setOnClickListener(this);

        user_name = (EditText) findViewById(R.id.user_name);
        captcha_edit = (EditText) findViewById(R.id.captcha_edit);
        user_pass = (EditText) findViewById(R.id.user_pass);
        user_passs = (EditText) findViewById(R.id.user_passs);


        count = new CountDownTimerUtils(captcha,60000, 1000,R.drawable.rounded_corners_18_lan, R.drawable.rounded_corners_18);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_back:

                this.setResult(RESULT_CODE);
                finish();

                break;

            /**
             * 获取验证码
             */
            case R.id.captcha:
                if(!user_name.getText().toString().trim().equals("")){
                    Log.e("user_name",user_name.getText().toString().trim());
                    count.start();
                    Dialogmanager.loadstart(this);
                    initCaptcha();

                }else{
                    ToastUtils.show(getApplicationContext(), "请输入手机号码");
                }


                break;
            case R.id.login_text:

                if(!user_pass.getText().toString().trim().equals(user_passs.getText().toString().trim())){
                    ToastUtils.show(getApplicationContext(), "密码不一致，请重新输入");
                    return;
                }


                if(!captcha_edit.getText().toString().trim().equals("")){
                    Dialogmanager.loadstart(this);
                    initRegistered();
                }else{
                    ToastUtils.show(getApplicationContext(), "请输入验证码");
                }

                break;





            default:

                break;
        }
    }


    private void initCaptcha() {

        Map<String,String> map = new HashMap<>();
        map.put("username",user_name.getText().toString().trim());
        OkHttp_Utils.PostMethods(map, UrlUtils.validationdxurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(1000);
                Log.e("validationdxurl",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(1000);
                Log.e("validationdxurl",response.toString());

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                       /* JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CourseCategoryModel>>() {}.getType();*/




                    }

                    ToastUtils.show(getApplicationContext(),json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRegistered() {
        Map<String,String> map = new HashMap<>();
        map.put("username",user_name.getText().toString().trim());
        map.put("regmcode",captcha_edit.getText().toString().trim());
        map.put("password",user_pass.getText().toString().trim());
        map.put("password2",user_passs.getText().toString().trim());
        OkHttp_Utils.PostMethods(map, UrlUtils.registerurl, new OkHttp_Utils.CallBack() {


            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(1000);
                Log.e("registerurl",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(1000);
                Log.e("registerurl",response.toString());

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CourseCategoryModel>>() {}.getType();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
