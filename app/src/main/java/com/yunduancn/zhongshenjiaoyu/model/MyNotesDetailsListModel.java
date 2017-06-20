package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyNotesDetailsListModel implements Serializable{
    /**
     * {
     "lessonId": "1041",
     "courseId": "202",
     "length": "20",
     "lessonname": "社区广场舞 火火的姑娘",
     "content": "<p>66666666666666666</p>\n\n<p> </p>"
     }
     */
    private String lessonId;
    private String courseId;
    private String length;
    private String lessonname;
    private String content;

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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLessonname() {
        return lessonname;
    }

    public void setLessonname(String lessonname) {
        this.lessonname = lessonname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MyNotesDetailsListModel{" +
                "lessonId='" + lessonId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", length='" + length + '\'' +
                ", lessonname='" + lessonname + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
