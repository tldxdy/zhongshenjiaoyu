package com.yunduancn.zhongshenjiaoyu.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.Update.UpdateModel;
import com.yunduancn.zhongshenjiaoyu.Update.utils.CretinAutoUpdateUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/5.
 */

public class MyApplication extends Application
{

    public static MyApplication application;

    @Override
    public void onCreate()
    {
        super.onCreate();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }*/

        // android 7.0系统解决拍照的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        application = this;


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        update();

    }

    private void update() {
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api
                .setBaseUrl(UrlUtils.url + "mapi_v1/appversion")
                //设置是否显示忽略此版本
                .setIgnoreThisVersion(true)
                //设置下载显示形式 对话框或者通知栏显示 二选一(Builder.TYPE_NITIFICATION通知栏更新，Builder.TYPE_DIALOG 对话框更新)

                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_NITIFICATION)
                //设置下载时展示的图标
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_POST)
                //设置自定义的Model类
                .setTransition(new UpdateModel())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }


}