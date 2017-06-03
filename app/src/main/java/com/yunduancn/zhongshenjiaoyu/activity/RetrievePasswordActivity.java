package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.CountDownTimerUtils;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class RetrievePasswordActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView back_image;
    TextView title, validation, modify_text;


    private EditText edit_user, edit_validation, edit_password;

    CountDownTimerUtils count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("修改密码");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);


        validation = (TextView) findViewById(R.id.validation);
        validation.setOnClickListener(this);

        modify_text = (TextView) findViewById(R.id.modify_text);
        modify_text.setOnClickListener(this);

        edit_user = (EditText) findViewById(R.id.edit_user);
        edit_validation = (EditText) findViewById(R.id.edit_validation);
        edit_password = (EditText) findViewById(R.id.edit_password);


        count = new CountDownTimerUtils(validation,60000, 1000,R.drawable.rounded_corners_4_lan, R.drawable.rounded_corners_4_hui);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:

                finish();
                break;

            case R.id.validation:
                if(!edit_user.getText().toString().trim().equals("")){
                    Log.e("user_name",edit_user.getText().toString().trim());
                    count.start();
                    Dialogmanager.loadstart(this);
                    initCaptcha();

                }else{
                    ToastUtils.show(getApplicationContext(), "请输入手机号码");
                }
                break;
            case R.id.modify_text:

                if(!edit_validation.getText().toString().trim().equals("")){
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
        map.put("username",edit_user.getText().toString().trim());
        OkHttp_Utils.PostMethods(map, UrlUtils.getcodeurl, new OkHttp_Utils.CallBack() {
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
        map.put("username",edit_user.getText().toString().trim());
        map.put("getcode",edit_validation.getText().toString().trim());
        map.put("password",edit_password.getText().toString().trim());
        OkHttp_Utils.PostMethods(map, UrlUtils.getpasswordurl, new OkHttp_Utils.CallBack() {


            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("registerurl",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("registerurl",response.toString());

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        RetrievePasswordActivity.this.finish();
                    }
                    ToastUtils.show(getApplicationContext(),json.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
