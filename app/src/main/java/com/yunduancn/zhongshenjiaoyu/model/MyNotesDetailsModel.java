package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyNotesDetailsModel implements Serializable{
    /**
     * {
     "courseId": "202",
     "pic": "public://course/2017/05-29/122935f6de10483332.jpg",
     "coursename": "社区广场舞 ",
     "noteNum": 3,
     "items": [
     */

    private String courseId;
    private String pic;
    private String coursename;
    private String noteNum;
    private List<MyNotesDetailsListModel> items;


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<MyNotesDetailsListModel> getItems() {
        return items;
    }

    public void setItems(List<MyNotesDetailsListModel> items) {
        this.items = items;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "MyNotesDetailsModel{" +
                "courseId='" + courseId + '\'' +
                ", pic='" + pic + '\'' +
                ", coursename='" + coursename + '\'' +
                ", noteNum='" + noteNum + '\'' +
                ", items=" + items +
                '}';
    }
}
