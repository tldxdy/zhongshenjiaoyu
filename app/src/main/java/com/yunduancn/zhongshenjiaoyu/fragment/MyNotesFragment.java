package com.yunduancn.zhongshenjiaoyu.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.decoration.GridSpacingItemDecoration;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.RecyclerTouchListener;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.listener.ScrollListener;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.MyNotesActivity;
import com.yunduancn.zhongshenjiaoyu.activity.MyNotesDetailsActivity;
import com.yunduancn.zhongshenjiaoyu.adapter.MyNotesAdapter;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesListModel;
import com.yunduancn.zhongshenjiaoyu.model.MyNotesModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/19.
 */

public class MyNotesFragment extends Fragment   implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{


    private List<MyNotesListModel> list;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerTouchListener onTouchListener;
    private GridLayoutManager mLayoutManager;
    private MyNotesAdapter mAdapter;


    private int pagesize = 10;

    private int currentpage = 1;

    private int total_number = -1;
    MyNotesModel myNotesModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    View view;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_n, container, false);
        context = getActivity();
        initView();
        onRefresh();

        return view;
    }

    private void initView() {

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.RefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.red));

        list = new ArrayList<>();
        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
        mAdapter = new MyNotesAdapter(context);
        mAdapter.setJZDataList(list);
        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        //添加触摸监听
        onTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);

        onTouchListener
                .setIndependentViews(R.id.examine_notes)
                .setViewsToFade(R.id.examine_notes)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        // ToastUtils.show(getApplicationContext(),mAdapter.getItemData(position).toString()+"");
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        if(mAdapter.getContentItemCount() == 0){
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setClass(context,MyNotesDetailsActivity.class);
                        intent.putExtra("myNotesListModel",mAdapter.getItemData(position));
                        startActivity(intent);
                        //ToastUtils.show(getApplicationContext(),mAdapter.getItemData(position).toString());
                    }
                });

        recyclerView.addOnItemTouchListener(onTouchListener);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

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


    private void initDatasx() {

        Map<String, String> map = new HashMap<>();
        map.put("pagesize", pagesize + "");
        map.put("currentpage", currentpage + "");
        map.put("userId", SharedPreferencesUtils.getValue(context, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.mynotesurl, new OkHttp_Utils.CallBack() {
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
                        Gson gson = new Gson();
                        Type type = new TypeToken<MyNotesModel>() {
                        }.getType();
                        myNotesModel = gson.fromJson(obj.toString(), type);
                        total_number = myNotesModel.getTotal();
                        if (myNotesModel.getItems() != null) {
                            list.clear();
                            list.addAll(myNotesModel.getItems());
                            mAdapter.setSXDataList(list);

                        }
                        if (list.size() != 10) {
                            mAdapter.setHasMoreDataAndFooter(false, true);
                        }else{
                            mAdapter.setHasMoreDataAndFooter(true, true);
                        }
                    } else {
                        ToastUtils.show(context.getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }


    private void initDatajz() {

        Map<String,String> map = new HashMap<>();
        map.put("pagesize",pagesize + "");
        map.put("currentpage",currentpage + "");
        map.put("userId", SharedPreferencesUtils.getValue(context, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.mynotesurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("response.toString()",response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<MyNotesModel>() {}.getType();
                        myNotesModel = gson.fromJson(obj.toString(),type);
                        total_number = myNotesModel.getTotal();
                        if(myNotesModel.getItems() != null){
                            list.clear();
                            list.addAll(myNotesModel.getItems());
                            mAdapter.setJZDataList(list);
                        }
                    }else{
                        ToastUtils.show(context.getApplicationContext(), json.getString("msg"));
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

