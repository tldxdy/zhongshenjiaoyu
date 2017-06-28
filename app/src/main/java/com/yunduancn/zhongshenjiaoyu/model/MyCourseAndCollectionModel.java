package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyCourseAndCollectionModel implements Serializable{
    /**
     * {
     "courseId": "202",
     "coursename": "社区广场舞 ",
     "pic": "http://ceshi.yunduancn.cn/files/course/2017/05-29/122935f6de10483332.jpg?7.5.12",
     "notesnum": 0,
     "threadnum": 0
     "lessonId":
     }
     */

    private String courseId;
    private String coursename;
    private String pic;
    private Integer notesnum;
    private Integer threadnum;
    private String lessonId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getNotesnum() {
        return notesnum;
    }

    public void setNotesnum(Integer notesnum) {
        this.notesnum = notesnum;
    }

    public Integer getThreadnum() {
        return threadnum;
    }

    public void setThreadnum(Integer threadnum) {
        this.threadnum = threadnum;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public String toString() {
        return "MyCourseAndCollectionModel{" +
                "courseId='" + courseId + '\'' +
                ", coursename='" + coursename + '\'' +
                ", pic='" + pic + '\'' +
                ", notesnum=" + notesnum +
                ", threadnum=" + threadnum +
                ", lessonId=" + lessonId +
                '}';
    }
}
