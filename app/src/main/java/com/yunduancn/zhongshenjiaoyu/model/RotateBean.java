package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RotateBean implements Serializable{
/**
 * {
 "pic": "http://edu.yunduancn.cn/files/system/mobile_picture1493956532.png",
 "url": "#1"
 }
 */


private String pic;
    private String url;

    public String getPic() {
        return pic;
    }

    public String getUrl() {
        return url;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RotateBean{" +
                "pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
