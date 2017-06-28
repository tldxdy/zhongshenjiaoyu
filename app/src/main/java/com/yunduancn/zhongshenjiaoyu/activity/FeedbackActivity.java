package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Administrator on 2017/6/26.
 */

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title, text_bc;
    private ImageView back_image;

    private EditText edit_name,editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_questions);


        initView();

    }
    private void initView() {

        title = (TextView) findViewById(R.id.title);
        title.setText("意见反馈");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        text_bc = (TextView) findViewById(R.id.text_bc);
        text_bc.setOnClickListener(this);
        text_bc.setHint("提交");

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.setHint("请填写您的手机号或QQ号，我们会在必要时候和您联系。");
        editText = (EditText) findViewById(R.id.editText);
        editText.setHint("写下您的问题和建议，我们将不断改进与尽快处理您的问题。");
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
        map.put("content",editText.getText().toString().trim());
        map.put("tel",edit_name.getText().toString().trim());

        OkHttp_Utils.PostMethods(map, UrlUtils.feedbackurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("feedbackurl", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");

                    if (code == 0) {
                        finish();
                        ToastUtils.show(getApplicationContext(), "谢谢您给我们的建议，我们会尽快处理，祝您生活愉快。");
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
