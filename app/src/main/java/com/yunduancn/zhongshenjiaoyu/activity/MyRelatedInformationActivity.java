package com.yunduancn.zhongshenjiaoyu.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_ChapterFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_CommentsFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_DetailsFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_ProblemFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.MyCourseFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.MyNotesFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.MyProblemFragment;
import com.yunduancn.zhongshenjiaoyu.model.CoursePlayModel;
import com.yunduancn.zhongshenjiaoyu.model.UserIndexModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyRelatedInformationActivity extends AppCompatActivity implements View.OnClickListener{



    private ImageView back_image, image_touxiang;

    private TextView name_text, zhiye_text, concern_num, fans_num, integral_num;


    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist;
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("动态", "课程", "手记","问题");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_related_information);

        initView();
        Dialogmanager.loadstart(this);
        initData();

    }



    private void initView() {
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(this);
        image_touxiang = (ImageView) findViewById(R.id.image_touxiang);

        name_text = (TextView) findViewById(R.id.name_text);
        zhiye_text = (TextView) findViewById(R.id.zhiye_text);
        zhiye_text.setVisibility(View.GONE);
        concern_num = (TextView) findViewById(R.id.concern_num);
        fans_num = (TextView) findViewById(R.id.fans_num);
        integral_num = (TextView) findViewById(R.id.integral_num);



        flist = new ArrayList<Fragment>();
        flist.add(new MyCourseFragment());

        flist.add(new MyCourseFragment());
        flist.add(new MyNotesFragment());
        flist.add(new MyProblemFragment());
        vp = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mIndicator.setVisibaleTabCount(flist.size());
        mIndicator.setTabItemTitles(mTitles);

        // mIndicator.setVisibalelincolr(Color.argb(200, 100, 200, 100));
        // mIndicator.setVisibalelincolr(getResources().getColor(R.color.blue));

        mIndicator.setViewPager(vp, 0);

        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
        wePagerAdapter = new WePagerAdapter(getSupportFragmentManager(), flist);
        vp.setAdapter(wePagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:
                finish();

                break;


            default:

                break;
        }
    }
    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.userindexurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                L.e("userindexurl",response.toString());
                Log.e("返回成功",response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONObject items = obj.getJSONObject("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserIndexModel>() {}.getType();
                        UserIndexModel userIndexModel = gson.fromJson(items.toString(),type);
                        Picasso.with(MyRelatedInformationActivity.this)
                                .load(userIndexModel.getLargeAvatar())
                                .placeholder(R.mipmap.me)
                                .error(R.mipmap.me)
                                .resize(100,100)
                                .into(image_touxiang);
                        name_text.setText(userIndexModel.getNickname());
                        concern_num.setText(userIndexModel.getFollowing());
                        fans_num.setText(userIndexModel.getFollower());
                        integral_num.setText(userIndexModel.getPoint());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
