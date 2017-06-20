package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */

public class NewsModel implements Serializable{

    /**
     * {
     "id": "47",
     "title": "全省高校及委厅推进“两学一做”学习教育常态化制度化工作部署会在昌举行 ",
     "thumb": "http://ceshi.yunduancn.cn/files/article/2017/05-31/114537119359761893.jpg",
     "publishedTime": "2017-05-31",
     "jianxu": "5月27日上午，全省高校及委厅推进“两学一做”学习教育常态化制度化工作部署会在南",
     "news_url": "http://ceshi.yunduancn.cn/mapi_v1/article/47"
     }
     */

    private String id;
    private String title;
    private String thumb;
    private String publishedTime;
    private String jianxu;
    private String news_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getJianxu() {
        return jianxu;
    }

    public void setJianxu(String jianxu) {
        this.jianxu = jianxu;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }
}
