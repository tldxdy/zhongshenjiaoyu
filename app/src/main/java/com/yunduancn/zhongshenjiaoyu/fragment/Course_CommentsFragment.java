package com.yunduancn.zhongshenjiaoyu.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.decoration.GridSpacingItemDecoration;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.RecyclerTouchListener;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.ScrollListener;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.VideoActivity;
import com.yunduancn.zhongshenjiaoyu.adapter.Course_CommentsAdapter;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesModel;
import com.yunduancn.zhongshenjiaoyu.model.ReviewsListModel;
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

public class Course_CommentsFragment extends Fragment  implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    public Course_CommentsFragment() {
        // Required empty public constructor
    }
    View view;


    private List<ReviewsListModel> list;



    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerTouchListener onTouchListener;
    private GridLayoutManager mLayoutManager;
    private Course_CommentsAdapter mAdapter;

    private String courseId;

    private int pagesize = 10;

    private int currentpage = 1;

    private int total_number = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course__comments, container, false);

        initView();

        return view;
    }

    private void initView() {
        courseId = VideoActivity.courseId;
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.red));

        list = new ArrayList<>();
        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
        mAdapter = new Course_CommentsAdapter(getContext());
        mAdapter.setJZDataList(list);
        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        //添加触摸监听
        onTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);

        onTouchListener
                .setIndependentViews(R.id.image_is_dz)
                .setViewsToFade(R.id.image_is_dz)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                //ToastUtils.show(getContext().getApplicationContext(),mAdapter.getItemData(position).toString()+"");
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {
                switch (independentViewID){
                    case R.id.image_is_dz:

                        break;
                }

            }
        });

        recyclerView.addOnItemTouchListener(onTouchListener);
    }



   /* private void initData() {
        list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(i + "");
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
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
        map.put("courseId", VideoActivity.courseId);
        OkHttp_Utils.PostMethods(map, UrlUtils.reviewsurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("response.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ReviewsListModel>>() {
                        }.getType();
                        List<ReviewsListModel> lists = gson.fromJson(array.toString(), type);
                            list.clear();
                            list.addAll(lists);
                            mAdapter.setJZDataList(list);

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }
                    } else {
                        ToastUtils.show(getContext().getApplicationContext(), json.getString("msg"));
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
        map.put("courseId", VideoActivity.courseId);
        OkHttp_Utils.PostMethods(map, UrlUtils.reviewsurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("response.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        int total = obj.getInt("total");
                        if(total == 0){

                        }else {
                            JSONArray array = obj.getJSONArray("items");
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<ReviewsListModel>>() {
                            }.getType();
                            List<ReviewsListModel> lists = gson.fromJson(array.toString(), type);
                            list.clear();
                            list.addAll(lists);
                            mAdapter.setSXDataList(list);
                        }

                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }
                    } else {
                        ToastUtils.show(getContext().getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (list.size() != 10) {
                        mAdapter.setHasMoreDataAndFooter(false, true);
                    }else{
                        mAdapter.setHasMoreDataAndFooter(true, true);
                    }
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


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
