package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.CountDownTimerUtils;

public class RetrievePasswordActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView back_image;
    TextView title, validation;


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


        count = new CountDownTimerUtils(validation,60000, 1000,R.drawable.rounded_corners_4_lan, R.drawable.rounded_corners_4_hui);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:

                finish();
                break;

            case R.id.validation:
                count.start();
                break;

            default:

                break;
        }
    }
}
