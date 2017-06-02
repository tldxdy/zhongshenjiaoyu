package com.yunduancn.zhongshenjiaoyu.application;

import android.app.Application;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.Update.UpdateModel;
import com.yunduancn.zhongshenjiaoyu.Update.utils.CretinAutoUpdateUtils;

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
        application = this;
        update();
       // HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        /*OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return null;
                    }
                })
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
*/
    }

    private void update() {
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api
                .setBaseUrl("http://101.201.31.212:8016/version/checkVersion")
                //设置是否显示忽略此版本
                .setIgnoreThisVersion(true)
                //设置下载显示形式 对话框或者通知栏显示 二选一(Builder.TYPE_NITIFICATION通知栏更新，Builder.TYPE_DIALOG 对话框更新)

                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_NITIFICATION)
                //设置下载时展示的图标
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                //设置自定义的Model类
                .setTransition(new UpdateModel())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }


}