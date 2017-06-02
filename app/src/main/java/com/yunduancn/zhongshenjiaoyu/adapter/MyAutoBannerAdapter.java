package com.yunduancn.zhongshenjiaoyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.RotateBean;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.view.AutoBannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class MyAutoBannerAdapter implements AutoBannerView.AutoBannerAdapter {
    List<RotateBean> urls;
    Context context;
    private LayoutInflater inflater;
    public MyAutoBannerAdapter(Context context) {
        this.context = context;
        this.urls = new ArrayList<>();
        inflater = LayoutInflater.from(context);

    }

    public void changeItems(List<RotateBean> urls, Context context) {
        this.urls.clear();
        this.urls.addAll(urls);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public View getView(View convertView, final int position) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.image_view_paving, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*Picasso.with(context).load(urls.get(position).getPic()).resize(400, 200)
                .centerCrop().into(viewHolder.img);*/

        Glide.with(context)
                .load(urls.get(position).getPic())
                .placeholder(R.color.cl_default)
                .error(R.color.white)
                .into(viewHolder.img);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(context.getApplicationContext(),urls.get(position).getPic() + "");
            }
        });


        return convertView;
    }
    class ViewHolder {
        ImageView img;
    }
}