package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class CourseChapterModel implements Serializable{

/**
 * "lessonId": "943",
 "courseId": "69",
 "title": "《百家讲坛》国史通鉴·秦汉三国篇 1 千古一帝_标清",
 "length": "42:00"
 */

   private String lessonId;
    private String courseId;
    private String title;
    private String length;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "CourseChapterModel{" +
                "lessonId='" + lessonId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", length='" + length + '\'' +
                '}';
    }
}
