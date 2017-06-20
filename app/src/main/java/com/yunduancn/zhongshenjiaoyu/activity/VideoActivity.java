package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.WePagerAdapter;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_ChapterFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_CommentsFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_DetailsFragment;
import com.yunduancn.zhongshenjiaoyu.fragment.Course_ProblemFragment;
import com.yunduancn.zhongshenjiaoyu.model.CoursePlayModel;
import com.yunduancn.zhongshenjiaoyu.utils.L;
import com.yunduancn.zhongshenjiaoyu.utils.MediaUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.view.ViewPagerIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoActivity extends AppCompatActivity implements Course_ChapterFragment.OnFragmentInteractionListener {
    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    View rootView;

    CoursePlayModel coursePlayModel;

    ViewPager vp;
    WePagerAdapter wePagerAdapter;
    List<Fragment> flist;
    ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("章节", "详情", "评论","问答");

    public static String courseId;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_video, null);
        setContentView(rootView);



        initView();
        initData();

    }
    private void initView() {
        /**虚拟按键的隐藏方法*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    //rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);

                }
            }
        });

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        coursePlayModel = (CoursePlayModel) getIntent().getSerializableExtra("coursePlayModel");
        courseId = getIntent().getStringExtra("courseId");

        flist = new ArrayList<Fragment>();
        flist.add(new Course_ChapterFragment());

        flist.add(new Course_DetailsFragment());
        flist.add(new Course_CommentsFragment());
        flist.add(new Course_ProblemFragment());
        vp = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mIndicator.setVisibaleTabCount(flist.size());
        mIndicator.setTabItemTitles(mTitles);

        // mIndicator.setVisibalelincolr(Color.argb(200, 100, 200, 100));
        // mIndicator.setVisibalelincolr(getResources().getColor(R.color.blue));

        mIndicator.setViewPager(vp, 0);

        vp.setOffscreenPageLimit(flist.size());//这表示你的预告加载的页面数量
        wePagerAdapter = new WePagerAdapter(getSupportFragmentManager(), flist);
        vp.setAdapter(wePagerAdapter);
    }



    private void initData() {

        list = new ArrayList<VideoijkBean>();

        VideoijkBean m1 = new VideoijkBean();
        m1.setStream("标清");
        m1.setUrl(coursePlayModel.getPayurl());
        list.add(m1);
        player = new PlayerView(this, rootView) {
            @Override
            public PlayerView toggleProcessDurationOrientation() {
                hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
            }

            @Override
            public PlayerView setPlaySource(List<VideoijkBean> list) {
                return super.setPlaySource(list);
            }
        }
                .setTitle(coursePlayModel.getTitle())
                .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)
                .hideSteam(true)
                .hideCenterPlayer(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load(coursePlayModel.getPic_url())
                                .placeholder(R.mipmap.banner)
                                .error(R.mipmap.banner)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(list)
                //是否收费
                //.setChargeTie(false,0)
                .startPlay();
    }




    /**
     * 播放本地视频
     */

    private String getLocalVideoPath(String name) {
        String sdCard = Environment.getExternalStorageDirectory().getPath();
        String uri = sdCard + File.separator + name;
        return uri;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, true);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        Log.e("eeee",player.getCurrentPosition()+"");
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
        /*new Handler().postDelayed(new Runnable(){

            public void run() {
                player.seekTo(6*60*1000);

            }

        }, 2000);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }


    @Override
    public void onFragmentInteraction(CoursePlayModel coursePlayModel) {
        L.e("onButtonPressed","activity ");
        vp.setOffscreenPageLimit(0);
        this.coursePlayModel = coursePlayModel;
        player.onDestroy();
        initData();
        vp.setOffscreenPageLimit(flist.size());
    }
}

