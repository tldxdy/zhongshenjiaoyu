package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MyrecordModel implements Serializable{

    /**
     * {
     "courseId": "206",
     "updatedTime": "2017-06-28",
     "lessonId": "1048",
     "coursename": "基金观察--一带一路-主题基金"
     }
     */

    private String courseId;
    private String updatedTime;
    private String lessonId;
    private String coursename;


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    @Override
    public String toString() {
        return "MyrecordModel{" +
                "courseId='" + courseId + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", lessonId='" + lessonId + '\'' +
                ", coursename='" + coursename + '\'' +
                '}';
    }
}
