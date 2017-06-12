package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class LecturerModel implements Serializable{
    /**
     *  "id": "29",
     "nickname": "方志远",
     "title": "江西师范大学教授",
     "avatar": "http://ceshi.yunduancn.cn/files/user/2017/05-20/0958179e5ac2899490.jpg?7.5.12"
     */

    private String id;
    private String nickname;
    private String title;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "LecturerModel{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
