package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;

public class PersonalInformationModifyActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title, determine,num;
    private EditText editText;
    private ImageView back_image;

    private String titleString, content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_modify);

        initView();
    }

    private void initView() {
        titleString = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");


        title = (TextView) findViewById(R.id.title);
        title.setText(titleString);
        num = (TextView) findViewById(R.id.num);

        determine = (TextView) findViewById(R.id.determine);
        determine.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        editText.setText(content);
        num.setText((128-editText.getText().toString().trim().length()) + "");
        editText.setSelection(content.length());

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }

            @Override
            public void afterTextChanged(Editable s) {
                num.setText((128-s.length()) + "");
                editText.setSelection(s.length());
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");

            }
        });


        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;
            case R.id.determine:
                Intent intent = new Intent(this,PersonalCenterActivity.class);
                intent.putExtra("information",editText.getText().toString().trim());
                setResult(105,intent);
                finish();

                break;

            default:

                break;

        }
    }
}
