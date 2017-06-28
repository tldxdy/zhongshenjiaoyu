package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ThreadShowListMdoel implements Serializable{

    /**
     * {
     "userId": "1",
     "createdTime": "1497861832",
     "content": "<p>通过测试问题</p>",
     "largeAvatar": "http://ceshi.yunduancn.cn/files/user/2017/04-25/0852375daffc224239.png?7.5.12",
     "nickname": "admin"
     }
     */
    private String userId;
    private String createdTime;
    private String content;
    private String largeAvatar;
    private String nickname;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "ThreadShowListMdoel{" +
                "userId='" + userId + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", content='" + content + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
