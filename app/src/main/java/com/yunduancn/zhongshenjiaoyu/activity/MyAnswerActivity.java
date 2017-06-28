package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.ThreadListModel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadShowMdoel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MyAnswerActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title, text_qr;
    private ImageView back_image;

    private EditText editText;

    private ThreadListModel threadListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);

        initView();
    }

    private void initView() {
        threadListModel = (ThreadListModel) getIntent().getSerializableExtra("threadListModel");

        title = (TextView) findViewById(R.id.title);
        title.setText("课程问答");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

        text_qr = (TextView) findViewById(R.id.text_qr);
        text_qr.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            case R.id.text_qr:
                if(editText.getText().toString().trim().length() == 0){
                    ToastUtils.show(getApplicationContext(), "请输入回答问题的内容");
                }
                Dialogmanager.loadstart(this);
                initData();
                break;

            default:

                break;
        }
    }

    private void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        map.put("content",editText.getText().toString().trim());
        map.put("threadId", threadListModel.getId() + "");
        map.put("courseId", threadListModel.getCourseId() + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.answerposturl, new OkHttp_Utils.CallBack() {
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
                        ToastUtils.show(getApplicationContext(), "提交成功");
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
