package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/14.
 */

public class UserIndexModel implements Serializable{

    /**
     * {
     "point": "0",
     "nickname": "m149645024677",
     "largeAvatar": "http://ceshi.yunduancn.cn/assets/img/default/avatar-large.png?7.5.12",
     "following": "0",
     "follower": "0"
     }
     */
    private String point;
    private String nickname;
    private String largeAvatar;
    private String following;
    private String follower;


    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    @Override
    public String toString() {
        return "UserIndexModel{" +
                "point='" + point + '\'' +
                ", nickname='" + nickname + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", following='" + following + '\'' +
                ", follower='" + follower + '\'' +
                '}';
    }
}
