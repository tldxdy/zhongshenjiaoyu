package com.yunduancn.zhongshenjiaoyu.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.CourseCategoryAdapter;
import com.yunduancn.zhongshenjiaoyu.model.CourseCategoryModel;
import com.yunduancn.zhongshenjiaoyu.model.RotateBean;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
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


public class CrowdFragment extends Fragment {


    GridView classification_gridview;


    CourseCategoryAdapter courseAdapter;

    List<CourseCategoryModel> list;


    private OnFragmentInteractionListener mListener;

    public CrowdFragment() {
    }
    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.course_center_fragment, container, false);

        initView();
        initData();


        return view;
    }

    private void initView() {
        classification_gridview = (GridView) view.findViewById(R.id.classification_gridview);
        list = new ArrayList<>();

    }


    private void initData() {


        OkHttp_Utils.PostMethods(new HashMap<String, String>(), UrlUtils.crowdurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Log.e("返回失败",e.toString());
            }

            @Override
            public void onMyResponse(String response, int id) {
                Log.e("response===",response.toString());

                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if(code == 0){
                        JSONObject obj = json.getJSONObject("obj");
                        //String items = obj.getString("items");
                        //ToastUtils.show(getActivity().getApplicationContext(), items.toString());
                        //Log.e("items===",items.toString());
                        JSONArray array = obj.getJSONArray("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CourseCategoryModel>>() {}.getType();
                        list = gson.fromJson(array.toString(),type);
                        courseAdapter = new CourseCategoryAdapter(getContext(),list);
                        classification_gridview.setAdapter(courseAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}