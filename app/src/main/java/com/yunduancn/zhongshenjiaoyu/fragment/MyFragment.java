package com.yunduancn.zhongshenjiaoyu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.MyCourseActivity;
import com.yunduancn.zhongshenjiaoyu.activity.MyNotesActivity;
import com.yunduancn.zhongshenjiaoyu.activity.MyRelatedInformationActivity;
import com.yunduancn.zhongshenjiaoyu.activity.SettingsActivity;
import com.yunduancn.zhongshenjiaoyu.model.UserIndexModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MyFragment extends Fragment implements OnClickListener{


    private OnFragmentInteractionListener mListener;

    public MyFragment() {
        // Required empty public constructor
    }
    View view;

    private TextView title, user_name, learning_time;

    private RelativeLayout user_information;

    private CircleImageView user_image;

    private LinearLayout concern_layout, fans_layout, integral_layout;
    private TextView concern_num, fans_num, integral_num;

    private LinearLayout mycourse_layout, mynotes_layout, myproblem_layout;

    private RelativeLayout historical_record_layout, myschedule_layout, mycollection_layout, settings_layout;


    UserIndexModel userIndexModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my, container, false);
        initView();


        return view;
    }

    private void initView() {
        title = (TextView) view.findViewById(R.id.title);
        title.setText("个人中心");
        user_information = (RelativeLayout) view.findViewById(R.id.user_information);
        user_information.setOnClickListener(this);

        user_name = (TextView) view.findViewById(R.id.user_name);
        learning_time = (TextView) view.findViewById(R.id.learning_time);
        user_image = (CircleImageView) view.findViewById(R.id.user_image);

        concern_layout = (LinearLayout) view.findViewById(R.id.concern_layout);
        concern_layout.setOnClickListener(this);
        fans_layout = (LinearLayout) view.findViewById(R.id.fans_layout);
        fans_layout.setOnClickListener(this);
        integral_layout = (LinearLayout) view.findViewById(R.id.integral_layout);
        integral_layout.setOnClickListener(this);
        concern_num = (TextView) view.findViewById(R.id.concern_num);
        fans_num = (TextView) view.findViewById(R.id.fans_num);
        integral_num = (TextView) view.findViewById(R.id.integral_num);

        mycourse_layout = (LinearLayout) view.findViewById(R.id.mycourse_layout);
        mycourse_layout.setOnClickListener(this);
        mynotes_layout = (LinearLayout) view.findViewById(R.id.mynotes_layout);
        mynotes_layout.setOnClickListener(this);
        myproblem_layout = (LinearLayout) view.findViewById(R.id.myproblem_layout);
        myproblem_layout.setOnClickListener(this);

        historical_record_layout = (RelativeLayout) view.findViewById(R.id.historical_record_layout);
        historical_record_layout.setOnClickListener(this);
        myschedule_layout = (RelativeLayout) view.findViewById(R.id.myschedule_layout);
        myschedule_layout.setOnClickListener(this);
        mycollection_layout = (RelativeLayout) view.findViewById(R.id.mycollection_layout);
        mycollection_layout.setOnClickListener(this);
        settings_layout = (RelativeLayout) view.findViewById(R.id.settings_layout);
        settings_layout.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart()","onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
        Log.e("onResume()","onResume()");
    }

    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(getContext(), Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.userindexurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {

            }

            @Override
            public void onMyResponse(String response, int id) {
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
                        userIndexModel = gson.fromJson(items.toString(),type);
                        Picasso.with(getContext().getApplicationContext())
                                .load(userIndexModel.getLargeAvatar())
                                .placeholder(R.mipmap.me)
                                .error(R.mipmap.me)
                                .resize(100,100)
                                .into(user_image);
                        user_name.setText(userIndexModel.getNickname());
                        concern_num.setText(userIndexModel.getFollowing());
                        fans_num.setText(userIndexModel.getFollower());
                        integral_num.setText(userIndexModel.getPoint());
                        learning_time.setVisibility(View.GONE);
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
        Log.e("onDetach()","onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause()","onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop()","onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy()","onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("onDestroyView()","onDestroyView()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("onActivityCreated()","onActivityCreated()");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.user_information:

                intent.setClass(getContext(), MyRelatedInformationActivity.class);
                startActivity(intent);

                break;
            case R.id.concern_layout:

                break;
            case R.id.fans_layout:

                break;
            case R.id.integral_layout:

                break;
            case R.id.mycourse_layout:

                intent.setClass(getContext(),MyCourseActivity.class);
                intent.putExtra("CourseAndCollection",0);
                startActivity(intent);

                break;
            case R.id.mynotes_layout:

                intent.setClass(getContext(), MyNotesActivity.class);
                startActivity(intent);

                break;
            case R.id.myproblem_layout:

                break;
            case R.id.historical_record_layout:

                break;
            case R.id.myschedule_layout:
                break;
            case R.id.mycollection_layout:

                intent.setClass(getContext(),MyCourseActivity.class);
                intent.putExtra("CourseAndCollection",1);
                startActivity(intent);

                break;
            case R.id.settings_layout:

                intent.setClass(getContext(), SettingsActivity.class);
                //intent.putExtra("userIndexModel",userIndexModel);
                startActivity(intent);


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
