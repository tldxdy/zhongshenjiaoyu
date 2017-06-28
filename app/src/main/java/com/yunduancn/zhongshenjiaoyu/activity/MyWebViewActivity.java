package com.yunduancn.zhongshenjiaoyu.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunduancn.zhongshenjiaoyu.LoadingSchedule.WebviewProgressDialog;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.L;

public class MyWebViewActivity extends AppCompatActivity {


    private TextView title;
    private ImageView back_image;
    private WebView webView;

    private String url;
    private String titles;
    private WebviewProgressDialog dialog;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);

        initView();
    }

    private void initView() {
        dialog = WebviewProgressDialog.createDialog(MyWebViewActivity.this);
        dialog.show();
        titles = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        L.e("url",url);
        title = (TextView) findViewById(R.id.title);
        title.setText(titles);
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //WebView就是一个简单的浏览器
        //android浏览器源码存在于LINUX/android/package/apps/Browser中
        //里面的所有操作都是围绕WebView来展开的
        webView = (WebView) findViewById(R.id.webView);




        //WebSettings 几乎浏览器的所有设置都在该类中进行
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true); //设置允许访问文件数据
        webSettings.setSupportZoom(false);

        webView.setWebViewClient(new DemoWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        /*
         * DemoJavaScriptInterface类为js调用android服务器端提供接口
         * android 作为DemoJavaScriptInterface类的客户端接口被js调用
         * 调用的具体方法在DemoJavaScriptInterface中定义：
         * 例如该实例中的clickOnAndroid
         */
        webView.addJavascriptInterface(new DemoJavaScriptInterface(),"myWebView");
        webView.loadUrl(url);    //对应当前project的asserts目录
        //也可以写file:///sdcard/index.html

        // mWebView.loadUrl("file:///android_asset/index2.html");

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >99) {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //Dialogmanager.loadfinsh(0);
                            dialog.dismiss();
                        }
                    }, 1000);

                } else {

                    dialog.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }

        });


    }

    class DemoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    final class DemoJavaScriptInterface {
        DemoJavaScriptInterface() {

        }
        private Handler mHandler = new Handler();
        /**
         * 该方法被浏览器端调用
         */
        public void clickOnAndroid() {
            mHandler.post(new Runnable() {
                public void run() {
                    //调用js中的wave()方法
                    webView.loadUrl("javascript:wave()");
                }
            });
        }
    }

    /**
     * 继承WebChromeClient类
     * 在这个类的3个方法中，分别使用Android的内置控件重写了Js中对应的对话框，就是说对js中的对话框做处理了，就是重写了。
     *
     */
    final class MyWebChromeClient extends WebChromeClient {

        /**
         * 处理alert弹出框
         */
        @Override
        public boolean onJsAlert(WebView view,String url,
                                 String message,final JsResult result) {
            L.d("webView","onJsAlert:"+message);
            //  mReusultText.setText("Alert:"+message);
            //对alert的简单封装
//            new AlertDialog.Builder(PhoneTest.this)
//                .setTitle("Alert")
//                .setMessage(message)
//                .setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                       // result.confirm();
//                         // MyWebView.this.finish();
//
//                   }
//            })
//            .setCancelable(true)
//            .create()
//            .show();

            result.confirm();      //不加上面的代码也能出框，为嘛呢？
            return true;
//            return super.onJsConfirm(view,url,message, result);
        }

        /**
         * 处理confirm弹出框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            L.d("webView", "onJsConfirm:"+message);
            //对confirm的简单封装
            new AlertDialog.Builder(MyWebViewActivity.this).
                    setTitle("Confirm")
                    .setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            })
                    .create()
                    .show();

            result.confirm();
            return true;
            //如果采用下面的代码会另外再弹出个消息框，目前不知道原理
            // 因为调用了父类的构造函数了。。。
            //return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 处理prompt弹出框
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            L.d("webView","onJsPrompt:"+message);
            result.confirm();
            return super.onJsPrompt(view, url, message, message, result);
        }
    }
}
