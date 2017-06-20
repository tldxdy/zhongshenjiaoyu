package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/20.
 */

public class ReviewsListModel implements Serializable{

    /**
     * {
     "id": "1",
     "userId": "23",
     "largeAvatar": null,
     "nickname": null,
     "courseId": "69",
     "content": "老师讲的非常不错",
     "rating": "5",
     "createdTime": "2017-05-24"
     }
     */

    private String id;
    private String userId;
    private String largeAvatar;
    private String nickname;
    private String courseId;
    private String content;
    private String rating;
    private String createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ReviewsListModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", courseId='" + courseId + '\'' +
                ", content='" + content + '\'' +
                ", rating='" + rating + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
