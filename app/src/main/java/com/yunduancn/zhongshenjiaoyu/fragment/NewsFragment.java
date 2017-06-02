package com.yunduancn.zhongshenjiaoyu.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.SimpleFooterView;
import com.yunduancn.zhongshenjiaoyu.MyRecyclerView.SwipeRecyclerView;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewAdapter;
import com.yunduancn.zhongshenjiaoyu.adapter.MyRecyclerViewHolder;
import com.yunduancn.zhongshenjiaoyu.model.NewsModel;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements SwipeRecyclerView.OnLoadListener, MyRecyclerViewAdapter.OnItemClickListener {


    private SwipeRecyclerView recyclerView;

    private MyRecyclerViewAdapter adapter;

    List<NewsModel> list;



    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }
    View view;

    private TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_news, container, false);

        initView();
        initData();

        return view;
    }

    //布局的初始化
    private void initView(){
        title = (TextView) view.findViewById(R.id.title);
        title.setText("新闻");



        recyclerView = (SwipeRecyclerView) view.findViewById(R.id.swipeRecyclerView);
        recyclerView.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.red));

        //set layoutManager
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerView.setOnLoadListener(this);
       // recyclerView.setRefreshing(true);
        //recyclerView.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 3));
        //recyclerView.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //禁止下拉刷新
        // recyclerView.setRefreshEnable(false);

        //禁止加载更多
        //recyclerView.setLoadMoreEnable(false);

        //设置emptyView
        /*TextView textView = new TextView(this);
        textView.setText("empty view");
        recyclerView.setEmptyView(textView);*/

        //设置footerView
        recyclerView.setFooterView(new SimpleFooterView(getActivity()));

        //由于SwipeRecyclerView中对GridLayoutManager的SpanSizeLookup做了处理，因此对于使用了
        //GridLayoutManager又要使用SpanSizeLookup的情况，可以这样使用！
        /*recyclerView.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3;
            }
        });*/

        //设置去除footerView 的分割线
       /* recyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(0xFFEECCCC);
                Rect rect = new Rect();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                final int childCount = parent.getChildCount() - 1;
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    //获得child的布局信息
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int itemDividerHeight = 1;//px
                    rect.set(left + 50, top, right - 50, top + itemDividerHeight);
                    c.drawRect(rect, paint);
                }
            }
        });*/

        //设置noMore
         //recyclerView.onNoMore("-- end --");

        //设置网络处理
        //recyclerView.onNetChange(true);

        //设置错误信息
        //recyclerView.onError("error");

    }




    private void initData() {


        list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            NewsModel news = new NewsModel();
            list.add(news);
        }
        adapter = new MyRecyclerViewAdapter<NewsModel>(getActivity(),R.layout.news_item,list){

            @Override
            public void onBindView(MyRecyclerViewHolder holder, int position) {
                TextView news_name=holder.getView(R.id.news_name);
                TextView news_introduction=holder.getView(R.id.news_introduction);
                ImageView imageView2 = holder.getView(R.id.imageView2);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        issss();
    }

    private void issss() {
        if(list.size() > 10){
            recyclerView.onNoMore("-- end --");
            recyclerView.setLoadMoreEnable(false);
        }else{
            recyclerView.onWZ("加载中...");
            recyclerView.setLoadMoreEnable(true);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //刷新的监听
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){

            public void run() {
                list.clear();
                for(int i = 0; i < 10; i++){
                    NewsModel news = new NewsModel();
                    list.add(news);
                }
                recyclerView.complete();
                adapter.notifyDataSetChanged();

                recyclerView.onLoadingMore();
                recyclerView.stopLoadingMore();
                issss();
            }

        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable(){

            public void run() {
                for(int i = 0; i < 5; i++){
                    NewsModel news = new NewsModel();
                    list.add(news);
                }
                if(list.size() > 20){
                    recyclerView.onNoMore("-- end --");
                }else{
                    recyclerView.onWZ("加载中...");
                    recyclerView.stopLoadingMore();
                    adapter.notifyDataSetChanged();
                }
            }

        }, 2000);
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.show(getActivity().getApplicationContext(), position + "");
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