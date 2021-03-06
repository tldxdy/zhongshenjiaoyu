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
import com.yunduancn.zhongshenjiaoyu.view.CustonRatingbar;
import com.yunduancn.zhongshenjiaoyu.view.RatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title, text_bc;
    private ImageView back_image;

    private EditText editText;

    public String courseId;

    RatingBar rateingbar;

    private static int  ratingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        initView();
    }

    private void initView() {
        courseId = getIntent().getStringExtra("courseId");

        title = (TextView) findViewById(R.id.title);
        title.setText("评论");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        text_bc = (TextView) findViewById(R.id.text_bc);
        text_bc.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
        rateingbar = (RatingBar)findViewById(R.id.rateingbar);
        rateingbar.setClickable(true);//设置可否点击
        rateingbar.setStar(0f);//设置显示的星星个数
        rateingbar.setStepSize(RatingBar.StepSize.Full);//设置每次点击增加一颗星还是半颗星
        rateingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                CommentsActivity.ratingCount = (int) ratingCount;
                Log.d("RatingBar","RatingBar-Count="+ratingCount);
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
        map.put("score",ratingCount + "");

        OkHttp_Utils.PostMethods(map, UrlUtils.createreviewsurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
                ToastUtils.show(getApplicationContext(),e.toString());
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
                        ToastUtils.show(getApplicationContext(), "评论成功，谢谢");
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
