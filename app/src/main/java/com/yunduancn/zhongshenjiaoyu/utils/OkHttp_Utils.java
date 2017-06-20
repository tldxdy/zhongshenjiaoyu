package com.yunduancn.zhongshenjiaoyu.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.yunduancn.zhongshenjiaoyu.application.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/8.
 */

public class OkHttp_Utils {

    public static void GetMethods(Map<String, String> map, String url, final CallBack callBack) {

        Map<String, String> map1 = getmap();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                map1.put(key, value);
            }
            map1.put("sign", pmutil.md5(map1));
            Log.e("e", map1.toString());
        }
        OkHttpUtils.get()
                .url(url)
                .params(map1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("返回失败",e.toString());
                        callBack.onMyError(call,e,id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("返回成功",response);
                        callBack.onMyResponse(response,id);
                    }
                });
    }

    public static void PostMethods(Map<String, String> map, String url, final CallBack callBack) {

        Map<String, String> map1 = getmap();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                map1.put(key, value);
            }

        }
        /*Map<String, String> map2 = new HashMap<>();
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue().toLowerCase();
            map2.put(key, value);
        }*/



        Log.e("e1111111", map1.toString());
        map1.put("sign", pmutil.md5(map1));
        //map1.put("sign", "adssssssasdasd");
        OkHttpUtils.post()
                .url(url)
                .params(map1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("返回失败",e.toString());
                        callBack.onMyError(call,e,id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("返回成功",response);
                        callBack.onMyResponse(response,id);
                    }
                });
    }


    public interface CallBack {

        public void onMyError(Call call, Exception e, int id);

        public void onMyResponse(String response, int id);
    }


    public static  Map<String, String> getmap() {
        String id= SharedPreferencesUtils.getValue(MyApplication.application.getApplicationContext(), Constant.AppName, "userId",null);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Ver", AppUtil.getVersionName(MyApplication.application));
        map.put("OS", AppUtil.getAppos());
        map.put("code",AppUtil.getVersionCode(MyApplication.application) + "");
        map.put("Timestamp", "" + AppUtil.getTimeStamp());
        Log.e("BaseUserId",id +"");
        if(id!=null&&!id.equals("")){
            map.put("BaseUserId", id);
        }else{
            map.put("BaseUserId", "");
        }

        return map;

    }
}
