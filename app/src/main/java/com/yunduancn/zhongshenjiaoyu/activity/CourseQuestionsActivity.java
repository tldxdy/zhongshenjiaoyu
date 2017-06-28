package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class CourseQuestionsActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title, text_bc;
    private ImageView back_image;

    private EditText edit_name,editText;

    public String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_questions);


        initView();
    }

    private void initView() {

        courseId = getIntent().getStringExtra("courseId");

        title = (TextView) findViewById(R.id.title);
        title.setText("提问");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        text_bc = (TextView) findViewById(R.id.text_bc);
        text_bc.setOnClickListener(this);

        edit_name = (EditText) findViewById(R.id.edit_name);
        editText = (EditText) findViewById(R.id.editText);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            case R.id.text_bc:
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
        map.put("courseId", courseId + "");
        map.put("title",edit_name.getText().toString().trim());

        OkHttp_Utils.PostMethods(map, UrlUtils.createrthreadurl, new OkHttp_Utils.CallBack() {
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
                        ToastUtils.show(getApplicationContext(), "问题已发布");
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
