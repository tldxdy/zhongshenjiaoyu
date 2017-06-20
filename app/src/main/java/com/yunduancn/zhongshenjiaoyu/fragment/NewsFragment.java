package com.yunduancn.zhongshenjiaoyu.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.SimpleFooterView;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.SwipeRecyclerView;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewHolder;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.model.ArticleCategoryModel;
import com.yunduancn.zhongshenjiaoyu.model.NewsModel;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


public class NewsFragment extends Fragment{


    private TextView title;

    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist = new ArrayList<Fragment>();
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = new ArrayList<>();

    public static List<ArticleCategoryModel> list;

    public static List<String> codes = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    public NewsFragment() {
        // Required empty public constructor
    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_news, container, false);

        initData();
        initView();

        return view;
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        OkHttp_Utils.PostMethods(map, UrlUtils.articlecategoryurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("articlecategoryurl", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        JSONArray array = obj.getJSONArray("category");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ArticleCategoryModel>>() {
                        }.getType();

                        list = gson.fromJson(array.toString(), type);
                        mTitles.clear();

                        flist.clear();
                        for (ArticleCategoryModel model:list){
                            mTitles.add(model.getName());

                            codes.add(model.getCode());
                        }
                        flist.add(new News1Fragment());
                        flist.add(new News2Fragment());
                        flist.add(new News3Fragment());
                        flist.add(new News4Fragment());
                        flist.add(new News5Fragment());
                        mIndicator.setVisibaleTabCount(flist.size());
                        mIndicator.setTabItemTitles(mTitles);

                        // mIndicator.setVisibalelincolr(Color.argb(200, 100, 200, 100));
                        // mIndicator.setVisibalelincolr(getResources().getColor(R.color.blue));

                        mIndicator.setViewPager(vp, 0);

                        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
                        wePagerAdapter = new WePagerAdapter(getChildFragmentManager(), flist);
                        vp.setAdapter(wePagerAdapter);
                       /* vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                mIndicator.setViewPager(vp,position);
                                vp.setCurrentItem(position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });*/

                    } else {
                        ToastUtils.show(getContext().getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    private void initView() {
        title = (TextView) view.findViewById(R.id.title);
        title.setText("新闻");


        vp = (ViewPager) view.findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
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