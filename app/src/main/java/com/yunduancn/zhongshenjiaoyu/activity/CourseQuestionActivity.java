package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.decoration.GridSpacingItemDecoration;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.RecyclerTouchListener;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.ScrollListener;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseListAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseQuestionAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseQuestionsAdapter;
import com.yunduancn.zhongshenjiaoyu.model.ThreadListModel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadShowListMdoel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadShowMdoel;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class CourseQuestionActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView title;
    private ImageView back_image,user_image;

    TextView text_title, user_name, text_time, text_introduction, image_is_gz, myquestion;

    ThreadListModel threadListModel;

    ThreadShowMdoel threadShowMdoel;
    List<ThreadShowListMdoel> list;


    CourseQuestionsAdapter mAdapter;
    private int pagesize = 10;

    private int currentpage = 1;

    private int total_number = -1;

    private RecyclerView recyclerView;
    private RecyclerTouchListener onTouchListener;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_question);

        initView();


    }
    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Log.i("RG", "source---?>>>" + source);
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                Log.i("RG", "url---?>>>" + url);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            Log.i("RG", "url---?>>>" + url);
            return drawable;
        }
    };


    private void initView() {
        threadListModel = (ThreadListModel) getIntent().getSerializableExtra("threadListModel");
        list = new ArrayList<>();

        title = (TextView) findViewById(R.id.title);
        title.setText("课程问答");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        user_image = (ImageView) findViewById(R.id.user_image);

        text_title = (TextView) findViewById(R.id.text_title);
        user_name = (TextView) findViewById(R.id.user_name);
        text_time = (TextView) findViewById(R.id.text_time);
        text_introduction = (TextView) findViewById(R.id.text_introduction);
        image_is_gz = (TextView) findViewById(R.id.image_is_gz);

        myquestion = (TextView) findViewById(R.id.myquestion);
        myquestion.setOnClickListener(this);


        text_title.setText(threadListModel.getTitle());
        user_name.setText(threadListModel.getNickname());
        text_time.setText(threadListModel.getCreatedTime() + "  |  楼主");

        text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        text_introduction.setText(Html.fromHtml(threadListModel.getContent(), imgGetter, null));
        Picasso.with(this)
                .load(threadListModel.getLargeAvatar())
                .placeholder(R.mipmap.me)
                .error(R.mipmap.me)
                .into(user_image);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
        mAdapter = new CourseQuestionsAdapter(this);
        mAdapter.setJZDataList(list);
        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        //添加触摸监听
        onTouchListener = new RecyclerTouchListener(this, recyclerView);

        onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {

            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        });

        recyclerView.addOnItemTouchListener(onTouchListener);

    }

    private ScrollListener scrollListener = new ScrollListener(mLayoutManager) {
        @Override
        public void onLoadMore() {
            loadMore();
        }
    };

    private void loadMore() {
        //模拟加载更多
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (list.size() < 10) {
                    mAdapter.setHasMoreDataAndFooter(false, true);
                    return;
                }
                currentpage++;
                initData();
                ScrollListener.setLoadMore(!ScrollListener.loadMore);
            }
        }, 1000);
    }


    private void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("pagesize", pagesize + "");
        map.put("currentpage", currentpage + "");
        map.put("threadId", threadListModel.getId() + "");
        map.put("courseId", threadListModel.getCourseId() + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.threadshowurl, new OkHttp_Utils.CallBack() {
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
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ThreadShowMdoel>() {
                        }.getType();
                        threadShowMdoel = gson.fromJson(obj.toString(), type);
                        list.clear();
                        if(threadShowMdoel.getItems() != null){
                            list.clear();
                            list.addAll(threadShowMdoel.getItems());
                            mAdapter.setJZDataList(list);
                        }

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }

                        text_title.setText(threadShowMdoel.getAskname());
                        user_name.setText(threadShowMdoel.getNickname());
                        text_time.setText(threadShowMdoel.getCreatedTime() + "  |  楼主");

                        text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                        text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                        text_introduction.setText(Html.fromHtml(threadShowMdoel.getAskcontent(), imgGetter, null));
                        Picasso.with(CourseQuestionActivity.this)
                                .load(threadShowMdoel.getLargeAvatar())
                                .placeholder(R.mipmap.me)
                                .error(R.mipmap.me)
                                .into(user_image);

                    } else {
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void initDatas() {

        Map<String, String> map = new HashMap<>();
        map.put("pagesize", pagesize + "");
        map.put("currentpage", currentpage + "");
        map.put("threadId", threadListModel.getId() + "");
        map.put("courseId", threadListModel.getCourseId() + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.threadshowurl, new OkHttp_Utils.CallBack() {
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
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ThreadShowMdoel>() {
                        }.getType();
                        threadShowMdoel = gson.fromJson(obj.toString(), type);
                        list.clear();
                        if(threadShowMdoel.getItems() != null){
                            list.clear();
                            list.addAll(threadShowMdoel.getItems());
                            mAdapter.setSXDataList(list);
                        }

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                            ScrollListener.setLoadMore(true);
                        }

                        text_title.setText(threadShowMdoel.getAskname());
                        user_name.setText(threadShowMdoel.getNickname());
                        text_time.setText(threadShowMdoel.getCreatedTime() + "  |  楼主");

                        text_introduction.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                        text_introduction.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                        text_introduction.setText(Html.fromHtml(threadShowMdoel.getAskcontent(), imgGetter, null));
                        Picasso.with(CourseQuestionActivity.this)
                                .load(threadShowMdoel.getLargeAvatar())
                                .placeholder(R.mipmap.me)
                                .error(R.mipmap.me)
                                .into(user_image);

                    } else {
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Dialogmanager.loadstart(this);
        currentpage = 1;
        initDatas();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            case R.id.myquestion:
                if(threadListModel == null){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(this,MyAnswerActivity.class);
                intent.putExtra("threadListModel", threadListModel);
                startActivity(intent);

                break;

            default:

                break;
        }
    }
}
