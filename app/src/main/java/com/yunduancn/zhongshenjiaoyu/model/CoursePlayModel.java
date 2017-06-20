package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CoursePlayModel implements Serializable{

    /**
     * {
     "title": "基金观察-公募",
     "length": "14:34",
     "baidu_mediaId": "mda-he4ihmuxxmseewqj",
     "payurl": "http://vod.jxzsxx.com/mda-he4ihmuxxmseewqj/mda-he4ihmuxxmseewqj.mp4",
     "pic_url": "http://vod.jxzsxx.com/mda-he4ihmuxxmseewqj/mda-he4ihmuxxmseewqj.jpg",
     "items": [
     {
     "lessonId": "1047",
     "courseId": "205",
     "title": "基金观察-公募",
     "length": "14:34"
     }
     ]
     }
     */

    private String title;
    private String length;
    private String baidu_mediaId;
    private String payurl;
    private String pic_url;
    private ArrayList<CourseChapterModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBaidu_mediaId() {
        return baidu_mediaId;
    }

    public void setBaidu_mediaId(String baidu_mediaId) {
        this.baidu_mediaId = baidu_mediaId;
    }

    public String getPayurl() {
        return payurl;
    }

    public void setPayurl(String payurl) {
        this.payurl = payurl;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public ArrayList<CourseChapterModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CourseChapterModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CoursePlayModel{" +
                "title='" + title + '\'' +
                ", length='" + length + '\'' +
                ", baidu_mediaId='" + baidu_mediaId + '\'' +
                ", payurl='" + payurl + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", items=" + items +
                '}';
    }
}
