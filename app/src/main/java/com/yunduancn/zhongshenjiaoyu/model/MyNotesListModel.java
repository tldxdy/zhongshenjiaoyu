package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyNotesListModel implements Serializable{
    /**
     * {
     "courseId": "202",
     "noteNum": "1",
     "noteLastUpdateTime": "2017-06-15",
     "coursename": "社区广场舞 ",
     "pic": "http://ceshi.yunduancn.cn/files/course/2017/05-29/122935f6de10483332.jpg?7.5.12"
     }
     */
    private String courseId;
    private String noteNum;
    private String noteLastUpdateTime;
    private String coursename;
    private String pic;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getNoteNum() {
        return noteNum;
    }

    public void setNoteNum(String noteNum) {
        this.noteNum = noteNum;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getNoteLastUpdateTime() {
        return noteLastUpdateTime;
    }

    public void setNoteLastUpdateTime(String noteLastUpdateTime) {
        this.noteLastUpdateTime = noteLastUpdateTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "MyNotesListModel{" +
                "courseId='" + courseId + '\'' +
                ", noteNum='" + noteNum + '\'' +
                ", noteLastUpdateTime='" + noteLastUpdateTime + '\'' +
                ", coursename='" + coursename + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
