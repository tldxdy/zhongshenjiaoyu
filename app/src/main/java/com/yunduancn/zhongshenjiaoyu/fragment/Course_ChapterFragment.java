package com.yunduancn.zhongshenjiaoyu.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.CourseInformationActivity;
import com.yunduancn.zhongshenjiaoyu.activity.VideoActivity;
import com.yunduancn.zhongshenjiaoyu.adapter.CoursePlayingItemAdapter;
import com.yunduancn.zhongshenjiaoyu.model.CourseChapterModel;
import com.yunduancn.zhongshenjiaoyu.model.CoursePlayModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


public class Course_ChapterFragment extends Fragment{



    private OnFragmentInteractionListener mListener;

    public Course_ChapterFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View view;

    private ListView listView;

    private CoursePlayingItemAdapter adapter;

    CoursePlayModel coursePlayModel;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course__chapter, container, false);


        initView();

        return view;
    }

    private void initView() {
        coursePlayModel = (CoursePlayModel) getActivity().getIntent().getSerializableExtra("coursePlayModel");
        userId = SharedPreferencesUtils.getValue(getContext(), Constant.AppName,"userId",null);
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new CoursePlayingItemAdapter(getContext(),coursePlayModel.getItems(),0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                initstudying(coursePlayModel.getItems().get(i));
                adapter.setI(i);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void initstudying(final CourseChapterModel courseChapterModel) {

        Map<String, String> map = new HashMap<>();
        map.put("courseId",courseChapterModel.getCourseId());
        map.put("lessonId",courseChapterModel.getLessonId());
        map.put("userid",  userId);
        OkHttp_Utils.PostMethods(map, UrlUtils.courseplayurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Log.e("courseplayurl",response.toString());
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<CoursePlayModel>() {
                        }.getType();
                        CoursePlayModel coursePlayModel = gson.fromJson(obj.toString(),type);
                        VideoActivity.courseId = courseChapterModel.getCourseId();
                        VideoActivity.lessonId = courseChapterModel.getLessonId();
                        onButtonPressed(coursePlayModel);

                    } else {

                        ToastUtils.show(getContext().getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }




    public void onButtonPressed(CoursePlayModel coursePlayModel) {
        if (mListener != null) {
            L.e("onButtonPressed","fragment");
            mListener.onFragmentInteraction(coursePlayModel);
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;
        }
    }

    /*
    * Deprecated on API 23
    * Use onAttachToContext instead
    */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(CoursePlayModel coursePlayModel);
    }
}
