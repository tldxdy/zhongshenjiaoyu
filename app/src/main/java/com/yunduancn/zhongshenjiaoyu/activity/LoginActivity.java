package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.MainActivity;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.CommonUtil;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{



    TextView login_text, forgot_password, user_registered;
    EditText user_name, user_pass;

    ImageView img_wx, img_wb, img_qq;

    final int REQUEST_CODE = 1;


    final int RESULT_CODE=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CommonUtil.setFullScreen(this);

        inintView();



    }

    private void inintView() {
        login_text = (TextView) findViewById(R.id.login_text);
        login_text.setOnClickListener(this);
        user_name = (EditText) findViewById(R.id.user_name);
        user_pass = (EditText) findViewById(R.id.user_pass);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(this);
        user_registered = (TextView) findViewById(R.id.user_registered);
        user_registered.setOnClickListener(this);

        img_wx = (ImageView) findViewById(R.id.img_wx);
        img_wx.setOnClickListener(this);
        img_wb = (ImageView) findViewById(R.id.img_wb);
        img_wb.setOnClickListener(this);
        img_qq = (ImageView) findViewById(R.id.img_qq);
        img_qq.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_text:
                Dialogmanager.loadstart(this);
                login();
                break;
            case R.id.forgot_password:
                startActivity(new Intent(this, RetrievePasswordActivity.class));

                break;
            case R.id.user_registered:


                startActivityForResult(new Intent(this,RegisterActivity.class),REQUEST_CODE);

                break;
            case R.id.img_wx:

                ToastUtils.show(getApplicationContext(), "暂不支持三三方登录，尽请期待");

                break;
            case R.id.img_wb:

                ToastUtils.show(getApplicationContext(), "暂不支持三三方登录，尽请期待");

                break;
            case R.id.img_qq:

                ToastUtils.show(getApplicationContext(), "暂不支持三三方登录，尽请期待");

                break;

            default:

                break;
        }
    }

    private void login() {
        Map<String,String> map = new HashMap<>();
        map.put("username",user_name.getText().toString().trim());
        map.put("password",user_pass.getText().toString().trim());
        OkHttp_Utils.PostMethods(map, UrlUtils.loginurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(1000);
                ToastUtils.show(getApplicationContext(), e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(1000);
                Log.e("response.toString()",response.toString());


               /* SharedPreferencesUtils.putValue(getApplicationContext(), Constant.AppName,"userId",19 + "");

                startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
                finish();*/
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){

                        JSONObject obj = json.getJSONObject("obj");

                        String userId = obj.getString("userid");

                        SharedPreferencesUtils.putValue(getApplicationContext(), Constant.AppName,"userId",userId + "");

                        startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
                        finish();

                    }

                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Log.e("data","requestCode:" + requestCode + ";resultCode:" + resultCode);

        if(requestCode==REQUEST_CODE) {
            if(resultCode==RESULT_CODE) {
                /**
                 * 注册返回
                 */
            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
