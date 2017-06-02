package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.SwipeRecyclerView;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewHolder;
import com.yunduancn.zhongshenjiaoyu.model.NewsModel;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MyCourseActivity extends AppCompatActivity implements View.OnClickListener, SwipeRecyclerView.OnLoadListener, MyRecyclerViewAdapter.OnItemClickListener {

    TextView title;

    ImageView back_image;

    private SwipeRecyclerView recyclerView;

    private MyRecyclerViewAdapter adapter;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        initView();
        initData();
    }


    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("我的课程");

        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        recyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecyclerView);
        recyclerView.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.red));

        //set layoutManager
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setOnLoadListener(this);

    }


    private void initData() {
        list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(i + "");
        }
        adapter = new MyRecyclerViewAdapter<String>(this,R.layout.my_course_item,list) {
            @Override
            public void onBindView(MyRecyclerViewHolder holder, int position) {
                TextView course_name = holder.getView(R.id.course_name);
                TextView course_describe = holder.getView(R.id.course_describe);
                TextView course_study = holder.getView(R.id.course_study);
                ImageView course_img = holder.getView(R.id.course_img);
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        issss();
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


    private void issss() {
        if(list.size() > 20){
            recyclerView.onNoMore("-- end --");
            recyclerView.setLoadMoreEnable(false);
        }else{
            recyclerView.onWZ("加载中...");
            recyclerView.setLoadMoreEnable(true);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){

            public void run() {
                list.clear();
                for(int i = 0; i < 10; i++){
                    list.add(i+"");
                }
                recyclerView.complete();
                adapter.notifyDataSetChanged();

                recyclerView.onLoadingMore();
                recyclerView.stopLoadingMore();
                issss();
            }

        }, 2000);

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable(){

            public void run() {
                for(int i = 0; i < 5; i++){
                    list.add(i + "");
                }
                if(list.size() > 20){
                    recyclerView.onNoMore("-- end --");
                }else{
                    recyclerView.onWZ("加载中...");
                    recyclerView.stopLoadingMore();
                    adapter.notifyDataSetChanged();
                }
            }

        }, 2000);

    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.show(getApplicationContext(), position + "");
    }
}
