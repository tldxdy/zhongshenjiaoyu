package com.yunduancn.zhongshenjiaoyu.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.CoursePlayingItemAdapter;
import com.yunduancn.zhongshenjiaoyu.model.CourseChapterModel;
import com.yunduancn.zhongshenjiaoyu.model.CoursePlayModel;
import com.yunduancn.zhongshenjiaoyu.model.CourseshowModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.MyScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class Course_DetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Course_DetailsFragment() {
    }

    View view;

    MyScrollView scrollView;

    RelativeLayout tob_layout;

    TextView text_introduce;

    private CoursePlayingItemAdapter adapter;
    private ListView listview;
    private List<CourseChapterModel> list;

    CoursePlayModel coursePlayModel;
    Context context;
    CourseshowModel courseshowModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course__details, container, false);
        initView();
        context = getActivity();
        initData();
        return view;
    }



    private void initView() {

        coursePlayModel = (CoursePlayModel) getActivity().getIntent().getSerializableExtra("coursePlayModel");

        text_introduce = (TextView) view.findViewById(R.id.text_introduce);

        listview = (ListView) view.findViewById(R.id.listview);

        list = new ArrayList<>();
        adapter = new CoursePlayingItemAdapter(getActivity(),list,-1);
        listview.setAdapter(adapter);

    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        if(coursePlayModel.getItems().size() != 0){
            map.put("courseId",coursePlayModel.getItems().get(0).getCourseId());
        }
        map.put("userid",  SharedPreferencesUtils.getValue(getContext(), Constant.AppName,"userId",null));

        OkHttp_Utils.PostMethods(map, UrlUtils.kcjsshowurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        Gson gson = new Gson();
                        Type type = new TypeToken<CourseshowModel>() {
                        }.getType();
                        courseshowModel = gson.fromJson(obj.toString(),type);
                        initFill();

                    } else {
                        ToastUtils.show(getContext().getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }

    private void initFill() {
        text_introduce.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        text_introduce.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        text_introduce.setText(Html.fromHtml(courseshowModel.getIntroduce(), imgGetter, null));


        list.clear();

        list.addAll(courseshowModel.getItems());
        adapter.notifyDataSetChanged();
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
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
