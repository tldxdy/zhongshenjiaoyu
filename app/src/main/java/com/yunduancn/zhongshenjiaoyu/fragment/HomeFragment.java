package com.yunduancn.zhongshenjiaoyu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.CourseInformationActivity;
import com.yunduancn.zhongshenjiaoyu.activity.CourseListActivity;
import com.yunduancn.zhongshenjiaoyu.activity.Coursectivity;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseCategoryAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.MyAutoBannerAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.NewAndHotCourseAdapter;
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;
import com.yunduancn.zhongshenjiaoyu.model.Coursesmodel;
import com.yunduancn.zhongshenjiaoyu.model.RotateBean;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.AutoBannerView;
import com.yunduancn.zhongshenjiaoyu.view.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class HomeFragment extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }
    View view;

    /**
     * 轮播图
     */
    private AutoBannerView autoBannerView;
    private MyAutoBannerAdapter autoBannerAdapter;
    private TextView title;
    List<RotateBean> list;

    ScrollView scrollView;
    private ImageView new_course_change;
    private MyGridView new_course_gridview;
    private NewAndHotCourseAdapter newCourseAdapter;
    List<Coursesmodel> newCoursesList;


    private ImageView hot_course_change;
    private MyGridView hot_course_gridview;
    private NewAndHotCourseAdapter hotCourseAdapter;
    List<Coursesmodel> hotCoursesList;

    List<CourseCategoryModel> courselist;
    private CourseAdapter courseAdapter;

    private MyGridView classification_gridview;


    private ImageView search_image;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        Dialogmanager.loadstart(getContext());
        initView();
        initData();
        initDataNew();
        initDataHot();
        initMore();

        return view;
    }




    private void initView() {
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        new_course_gridview = (MyGridView) view.findViewById(R.id.new_course_gridview);
        new_course_change = (ImageView) view.findViewById(R.id.new_course_change);
        new_course_change.setOnClickListener(this);

        newCoursesList = new ArrayList<>();

        newCourseAdapter = new NewAndHotCourseAdapter(getContext(),newCoursesList);
        new_course_gridview.setAdapter(newCourseAdapter);

        new_course_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), CourseInformationActivity.class);
                intent.putExtra("coursesmodel",newCoursesList.get(i));
                startActivity(intent);
            }
        });

        hot_course_gridview = (MyGridView) view.findViewById(R.id.hot_course_gridview);
        hot_course_change = (ImageView) view.findViewById(R.id.hot_course_change);
        hot_course_change.setOnClickListener(this);

        hotCoursesList = new ArrayList<>();
        hotCourseAdapter = new NewAndHotCourseAdapter(getContext(),hotCoursesList);
        hot_course_gridview.setAdapter(hotCourseAdapter);
        hot_course_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), CourseInformationActivity.class);
                intent.putExtra("coursesmodel",hotCoursesList.get(i));
                startActivity(intent);
            }
        });

        courselist = new ArrayList<>();
        classification_gridview = (MyGridView) view.findViewById(R.id.classification_gridview);
        courseAdapter = new CourseAdapter(getContext(),courselist);
        classification_gridview.setAdapter(courseAdapter);

        classification_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 7){
                    Intent intent = new Intent();
                    intent.setClass(getContext(), CourseListActivity.class);
                    intent.putExtra("course",courselist.get(i));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(getContext(), Coursectivity.class);
                    startActivity(intent);


                }
            }
        });



        scrollView.smoothScrollTo(0, 0);

        autoBannerView = (AutoBannerView) view.findViewById(R.id.autoBannerView);
        title = (TextView) view.findViewById(R.id.bannerTitle);
        autoBannerView.setDotGravity(AutoBannerView.DotGravity.CENTER);
        autoBannerView.setWaitMilliSceond(3000);
        autoBannerView.setDotMargin(5);
        autoBannerView.setOnBannerChangeListener(onBannerChangeListener);



        search_image = (ImageView) view.findViewById(R.id.search_image);
        search_image.setOnClickListener(this);

    }
    /**
     * 获取轮播数据
     */
    private void initData() {
        autoBannerAdapter = new MyAutoBannerAdapter(getContext());
        list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("position","");
        map.put("cityid","");
        OkHttp_Utils.PostMethods(map, UrlUtils.adbannerurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Log.e("返回失败",e.toString());
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("返回成功",response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<RotateBean>>() {}.getType();
                        ArrayList<RotateBean> lists = gson.fromJson(array.toString(),type);
                        list.clear();
                        list.addAll(lists);


                        autoBannerAdapter.changeItems(list, getContext());
                        autoBannerView.setAdapter(autoBannerAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 课程分类
      */
    private void initMore() {
        Map<String,String> map = new HashMap<>();
        OkHttp_Utils.PostMethods(map, UrlUtils.hotclassurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回失败",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回成功",response);

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CourseCategoryModel>>() {}.getType();
                        ArrayList<CourseCategoryModel> lists = gson.fromJson(array.toString(),type);
                        courselist.clear();
                        courselist.addAll(lists);

                        /*CourseCategoryModel model = new CourseCategoryModel();
                        model.setClassname("更多课程");
                        model.setPic(R.mipmap.gengduo + "");
                        courselist.add(model);*/
                        courseAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 最新课程
     */
    private void initDataNew() {
        Map<String,String> map = new HashMap<>();
        map.put("position","0");
        OkHttp_Utils.PostMethods(map, UrlUtils.coursesindexurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回失败",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回成功",response);

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Coursesmodel>>() {}.getType();
                        ArrayList<Coursesmodel> lists = gson.fromJson(array.toString(),type);
                        newCoursesList.clear();
                        newCoursesList.addAll(lists);
                        newCourseAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    /**
     * 热门课程
     */
    private void initDataHot() {
        Map<String,String> map = new HashMap<>();
        map.put("position","1");
        OkHttp_Utils.PostMethods(map, UrlUtils.coursesindexurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回失败",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(2000);
                Log.e("返回成功",response);

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Coursesmodel>>() {}.getType();
                        ArrayList<Coursesmodel> lists = gson.fromJson(array.toString(),type);
                        hotCoursesList.clear();
                        hotCoursesList.addAll(lists);
                        hotCourseAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private AutoBannerView.OnBannerChangeListener onBannerChangeListener = new AutoBannerView.OnBannerChangeListener() {
        @Override
        public void onCurrentItemChanged(int position) {
            //title.setText(list.get(position).getName());
        }

    };


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_course_change:
                Dialogmanager.loadstart(getContext());
                initDataNew();
                break;
            case R.id.hot_course_change:
                Dialogmanager.loadstart(getContext());
                initDataHot();
                break;


            case R.id.search_image:

                ToastUtils.show(getContext().getApplicationContext(),"点击搜索");
                break;

            default:

                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
