package com.yunduancn.zhongshenjiaoyu.utils;

import android.content.Context;
import android.os.Handler;

import com.yunduancn.zhongshenjiaoyu.LoadingSchedule.CustomProgressDialog;

/**
 * Created by Administrator on 2017/5/11.
 */

public class Dialogmanager {
    static CustomProgressDialog dialog;
    static Handler handler=new Handler();
    public static void loadfinsh(int longtime) {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                dialog.dismiss();
            }
        }, longtime);


    }

    public static void loadstart(Context mcontext) {
        dialog = CustomProgressDialog.createDialog(mcontext);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
}
