package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ThreadShowMdoel implements Serializable{
    /**
     * {
     "askname": "测试问题啊",
     "askcontent": "<p>测试问题啊</p>",
     "userId": "19",
     "hitNum": "7",
     "largeAvatar": "http://ceshi.yunduancn.cn/assets/img/default/avatar-large.png?7.5.12",
     "nickname": "xiaowang",
     "createdTime": "2017-06-19",
     "total": "1",
     "items":[]
     }
     */

    private String askname;
    private String askcontent;
    private String userId;
    private String hitNum;
    private String largeAvatar;
    private String nickname;
    private String createdTime;
    private String total;
    private List<ThreadShowListMdoel> items;

    public String getAskname() {
        return askname;
    }

    public void setAskname(String askname) {
        this.askname = askname;
    }

    public String getAskcontent() {
        return askcontent;
    }

    public void setAskcontent(String askcontent) {
        this.askcontent = askcontent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHitNum() {
        return hitNum;
    }

    public void setHitNum(String hitNum) {
        this.hitNum = hitNum;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<ThreadShowListMdoel> getItems() {
        return items;
    }

    public void setItems(List<ThreadShowListMdoel> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ThreadShowMdoel{" +
                "askname='" + askname + '\'' +
                ", askcontent='" + askcontent + '\'' +
                ", userId='" + userId + '\'' +
                ", hitNum='" + hitNum + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", total='" + total + '\'' +
                ", items=" + items +
                '}';
    }
}
