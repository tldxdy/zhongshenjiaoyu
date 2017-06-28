package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.R;

public class AboutUsActivity extends AppCompatActivity {

    private TextView title, text_neirong, text_zk;
    private ImageView back_image, image_fx;
    LinearLayout layout_zk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("关于我们");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image_fx = (ImageView) findViewById(R.id.image_fx);

        text_zk = (TextView) findViewById(R.id.text_zk);

        text_neirong = (TextView) findViewById(R.id.text_neirong);

        layout_zk = (LinearLayout) findViewById(R.id.layout_zk);
        layout_zk.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    text_neirong.setEllipsize(null);// 展开
                    text_neirong.setSingleLine(flag);
                    image_fx.setImageResource(R.mipmap.xiangshang);
                    text_zk.setText("收起");
                } else {
                    flag = true;
                    text_neirong.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    text_neirong.setLines(5);
                    image_fx.setImageResource(R.mipmap.xiangxia);
                    text_zk.setText("展开");
                }
            }
        });


    }
}
