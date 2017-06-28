package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/20.
 */

public class ThreadListModel implements Serializable{

    /**
     *
     */

    private String id;
    private String userId;
    private String largeAvatar;
    private String nickname;
    private String courseId;
    private String content;
    private String title;
    private String createdTime;
    private String lessonId;
    private String coursename;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
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

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }


    @Override
    public String toString() {
        return "ThreadListModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", courseId='" + courseId + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", lessonId='" + lessonId + '\'' +
                ", coursename='" + coursename + '\'' +
                '}';
    }
}
