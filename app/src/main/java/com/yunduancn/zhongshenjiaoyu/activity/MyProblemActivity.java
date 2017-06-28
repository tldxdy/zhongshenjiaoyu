package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.decoration.GridSpacingItemDecoration;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.RecyclerTouchListener;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.ScrollListener;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.MyNotesAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.MyProblemAdapter;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesModel;
import com.yunduancn.zhongshenjiaoyu.model.ThreadListModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyProblemActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private TextView title;
    private ImageView back_image;

    private List<ThreadListModel> list;



    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerTouchListener onTouchListener;
    private GridLayoutManager mLayoutManager;
    private MyProblemAdapter mAdapter;


    private int pagesize = 10;

    private int currentpage = 1;

    private int total_number = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_problem);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Dialogmanager.loadstart(this);
        onRefresh();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("我的问题");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.RefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.red));

        list = new ArrayList<>();
        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
        mAdapter = new MyProblemAdapter(this);
        mAdapter.setJZDataList(list);
        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        //添加触摸监听
        onTouchListener = new RecyclerTouchListener(this, recyclerView);

        onTouchListener
                /*.setIndependentViews(R.id.image_is_dz)
                .setViewsToFade(R.id.image_is_dz)*/
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        if(mAdapter.getContentItemCount() == 0){
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setClass(MyProblemActivity.this, CourseQuestionActivity.class);
                        intent.putExtra("threadListModel",mAdapter.getItemData(position));
                        startActivity(intent);

                        //ToastUtils.show(getContext().getApplicationContext(),mAdapter.getItemData(position).toString()+"");
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                       /* switch (independentViewID){
                            case R.id.image_is_dz:

                                break;
                        }*/

                    }
                });

        recyclerView.addOnItemTouchListener(onTouchListener);

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
                initDatajz();
                ScrollListener.setLoadMore(!ScrollListener.loadMore);
            }
        }, 1000);
    }


    private void initDatajz() {
        Map<String, String> map = new HashMap<>();
        map.put("pagesize", pagesize + "");
        map.put("currentpage", currentpage + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.myaskurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("myaskurl.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ThreadListModel>>() {
                        }.getType();
                        List<ThreadListModel> lists = gson.fromJson(array.toString(), type);
                        list.clear();
                        list.addAll(lists);
                        mAdapter.setJZDataList(list);

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }
                    } else {
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }



    private void initDatasx() {
        Map<String, String> map = new HashMap<>();
        map.put("pagesize", pagesize + "");
        map.put("currentpage", currentpage + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.myaskurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("myaskurl.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ThreadListModel>>() {
                        }.getType();
                        List<ThreadListModel> lists = gson.fromJson(array.toString(), type);
                        list.clear();
                        list.addAll(lists);
                        mAdapter.setSXDataList(list);

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }
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
    public void onRefresh() {
        //避免刷新过快
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentpage = 1;
                initDatasx();
                mRefreshLayout.setRefreshing(false);
                ScrollListener.setLoadMore(true);
            }
        }, 1000);
    }
}
