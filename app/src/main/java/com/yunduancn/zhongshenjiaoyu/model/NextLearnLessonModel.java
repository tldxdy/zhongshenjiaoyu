package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9.
 */

public class NextLearnLessonModel implements Serializable{
    /**
     * {
     "lessonId": "1048",
     "courseId": "206",
     "title": "基金观察--一带一路-主题基金"
     }
     */

    private String lessonId;
    private String courseId;
    private String title;

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

    @Override
    public String toString() {
        return "NextLearnLessonModel{" +
                "lessonId='" + lessonId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
