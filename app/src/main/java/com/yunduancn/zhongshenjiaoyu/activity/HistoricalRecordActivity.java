package com.yunduancn.zhongshenjiaoyu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.adapter.HistoricalRecordAdapter;
import com.yunduancn.zhongshenjiaoyu.model.MyrecordModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.Call;

public class HistoricalRecordActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back_image;


    private ListView listView;

    private List<String> listString;
    private List<ArrayList<MyrecordModel>> list;
    private HistoricalRecordAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_record);


        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("历史记录");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);

        listString = new ArrayList<>();
        list = new ArrayList<>();

        adapter = new HistoricalRecordAdapter(this,list,listString);
        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:

                finish();
                break;

            default:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dialogmanager.loadstart(this);
        initData();

    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null));
        OkHttp_Utils.PostMethods(map, UrlUtils.myrecordurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                Log.e("myaskurl.toString()", response.toString());
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == 0) {
                        JSONObject obj = json.getJSONObject("obj");
                        JSONObject items = obj.getJSONObject("items");
                        Gson gson = new Gson();
                        Type type = new TypeToken<Map<String,ArrayList<MyrecordModel>>>() {
                        }.getType();
                        LinkedTreeMap<String,ArrayList<MyrecordModel>> hashMap = gson.fromJson(items.toString(), type);

                        listString.clear();
                        list.clear();
                        Iterator it = hashMap.entrySet().iterator();
                          while (it.hasNext()) {
                           // entry的输出结果如key0=value0等
                           Map.Entry entry =(Map.Entry) it.next();
                           String key = (String) entry.getKey();
                              listString.add(key);
                           ArrayList<MyrecordModel> value= (ArrayList<MyrecordModel>) entry.getValue();
                              list.add(value);
                          }
                            adapter.notifyDataSetChanged();

                        Log.e("listString.toString()", listString.toString());
                        Log.e("list.toString()", list.toString());
                    } else {
                        ToastUtils.show(getApplicationContext(), json.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }


}
