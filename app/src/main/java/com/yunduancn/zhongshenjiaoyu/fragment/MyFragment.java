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

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.MyCourseActivity;
import com.yunduancn.zhongshenjiaoyu.activity.SettingsActivity;
import com.yunduancn.zhongshenjiaoyu.view.RoundImageView;

public class MyFragment extends Fragment implements OnClickListener{


    private OnFragmentInteractionListener mListener;

    public MyFragment() {
        // Required empty public constructor
    }
    View view;

    private TextView title, user_name, learning_time;

    private RelativeLayout user_information;

    private RoundImageView user_image;

    private LinearLayout concern_layout, fans_layout, integral_layout;
    private TextView concern_num, fans_num, integral_num;

    private LinearLayout mycourse_layout, mynotes_layout, myproblem_layout;

    private RelativeLayout historical_record_layout, myschedule_layout, mycollection_layout, settings_layout;

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
        user_image = (RoundImageView) view.findViewById(R.id.user_image);

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
        Log.e("onResume()","onResume()");
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

                break;
            case R.id.concern_layout:

                break;
            case R.id.fans_layout:

                break;
            case R.id.integral_layout:

                break;
            case R.id.mycourse_layout:

                intent.setClass(getContext(),MyCourseActivity.class);
                startActivity(intent);

                break;
            case R.id.mynotes_layout:

                break;
            case R.id.myproblem_layout:

                break;
            case R.id.historical_record_layout:

                break;
            case R.id.myschedule_layout:
                break;
            case R.id.mycollection_layout:

                break;
            case R.id.settings_layout:

                intent.setClass(getContext(), SettingsActivity.class);
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
