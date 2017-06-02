package com.yunduancn.zhongshenjiaoyu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/11.
 */

public class ToastUtils {
    private static Toast TOAST;
    private static final String TAG = "ToastUtil";


    //短时间吐司

    /**
     *
     * @param context ApplicationContext
     * * @param resourceID
     */
    public static void show(Context context, int resource) {
        show(context, resource, Toast.LENGTH_SHORT);
    }

    //短时间吐司
    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    //自定义时长吐司
    public static void show(Context context, Integer resourceID, int duration) {
        String text = context.getResources().getString(resourceID);// 用于显示的文字
        show(context, text, duration);
    }

    //自定义时长吐司
    public static void show(final Context context, final String text, final int duration) {

        if (TOAST == null) {
            TOAST = Toast.makeText(context, text, duration);
        } else {
            TOAST.setText(text);
            TOAST.setDuration(duration);
        }

        TOAST.show();
    }


}