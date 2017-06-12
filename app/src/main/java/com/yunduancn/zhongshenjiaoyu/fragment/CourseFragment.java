package com.yunduancn.zhongshenjiaoyu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.BaiduVideo.info.VideoInfo;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.activity.BDVideoActivity;
import com.yunduancn.zhongshenjiaoyu.activity.VideoActivity;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CourseFragment extends Fragment {


    private TextView title;

    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist;
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("人群", "课程中心", "学科中心");




    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
    }
    View view;

    //Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_course, container, false);

        /*button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                getContext().startActivity(intent);
            }
        });

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BDVideoActivity.class);
                VideoInfo info = new VideoInfo(" 测试" , "http://edu.yunduancn.cn/mapi_v1/courses/56/lessons/533/media?token=");
                intent.putExtra("videoInfo", info);
                getContext().startActivity(intent);
            }
        });*/




        initView();
        return view;
    }

    private void initView() {
        title = (TextView) view.findViewById(R.id.title);
        title.setText("课程");

        flist = new ArrayList<Fragment>();
        flist.add(new CrowdFragment());

        flist.add(new CourseCenterFragment());
        flist.add(new DisciplineCenterFragment());
        vp = (ViewPager) view.findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
        mIndicator.setVisibaleTabCount(flist.size());
        mIndicator.setTabItemTitles(mTitles);

        // mIndicator.setVisibalelincolr(Color.argb(200, 100, 200, 100));
        // mIndicator.setVisibalelincolr(getResources().getColor(R.color.blue));

        mIndicator.setViewPager(vp, 0);

        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
        wePagerAdapter = new WePagerAdapter(getActivity().getSupportFragmentManager(), flist);
        vp.setAdapter(wePagerAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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