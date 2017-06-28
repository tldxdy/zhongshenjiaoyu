package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
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

public class MyNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title, text_bc;
    private ImageView back_image;

    private EditText editText;

    public String courseId;

    public String lessonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initView();
        Dialogmanager.loadstart(this);
        initData();
    }

    private void initView() {
        courseId = getIntent().getStringExtra("courseId");
        lessonId = getIntent().getStringExtra("lessonId");


        title = (TextView) findViewById(R.id.title);
        title.setText("记笔记");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        text_bc = (TextView) findViewById(R.id.text_bc);
        text_bc.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);

    }

    private void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        map.put("lessonId", lessonId + "");

        OkHttp_Utils.PostMethods(map, UrlUtils.viewpluginurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("viewpluginurl", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");

                    if (code == 0) {

                        JSONObject obj = json.getJSONObject("obj");
                        editText.setText(obj.getString("content").toString());
                        editText.setSelection(editText.getText().length());


                    }else{
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            case R.id.text_bc:
                Dialogmanager.loadstart(this);
                initSubmit();
                break;

            default:

                break;
        }
    }



    private void initSubmit() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        map.put("content",editText.getText().toString().trim());
        map.put("lessonId", lessonId + "");
        map.put("courseId", courseId + "");
        map.put("status","true");

        OkHttp_Utils.PostMethods(map, UrlUtils.lessonpluginurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("threadshowurl", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");

                    if (code == 0) {
                        finish();
                        ToastUtils.show(getApplicationContext(), "保存手记");
                    }else{
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
