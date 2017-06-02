package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener{


    TextView title;

    ImageView back_image;

    RelativeLayout touxian_layout, profession_layout, signature_layout, nickname_layout, birthday_layout, region_layout, binding_phone_layout, binding_email_layout, real_name_layout, school_registration_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);


        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("个人信息");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        touxian_layout = (RelativeLayout) findViewById(R.id.touxian_layout);
        touxian_layout.setOnClickListener(this);
        profession_layout = (RelativeLayout) findViewById(R.id.profession_layout);
        profession_layout.setOnClickListener(this);
        signature_layout = (RelativeLayout) findViewById(R.id.signature_layout);
        signature_layout.setOnClickListener(this);
        nickname_layout = (RelativeLayout) findViewById(R.id.nickname_layout);
        nickname_layout.setOnClickListener(this);
        birthday_layout = (RelativeLayout) findViewById(R.id.birthday_layout);
        birthday_layout.setOnClickListener(this);
        region_layout = (RelativeLayout) findViewById(R.id.region_layout);
        region_layout.setOnClickListener(this);
        binding_phone_layout = (RelativeLayout) findViewById(R.id.binding_phone_layout);
        binding_phone_layout.setOnClickListener(this);
        binding_email_layout = (RelativeLayout) findViewById(R.id.binding_email_layout);
        binding_email_layout.setOnClickListener(this);
        real_name_layout = (RelativeLayout) findViewById(R.id.real_name_layout);
        real_name_layout.setOnClickListener(this);
        school_registration_layout = (RelativeLayout) findViewById(R.id.school_registration_layout);
        school_registration_layout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:
                finish();
                break;
        }
    }
}
