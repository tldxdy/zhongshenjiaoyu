package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/17.
 */

public class Coursesmodel implements Serializable {

/**
 * {
 "id": "66",
 "coursename": "大红袍-泡茶步骤",
 "teachers": "admin",
 "seen": "5",
 "pic": "http://www.jxsqjy.cn/files/course/2017/05-17/160418214780320276.jpg?7.5.12"
 "introduction": ""
 }
 */
    private String id;
    private String coursename;
    private String teachers;
    private String seen;
    private String pic;
    private String introduction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Coursesmodel{" +
                "id='" + id + '\'' +
                ", coursename='" + coursename + '\'' +
                ", teachers='" + teachers + '\'' +
                ", seen='" + seen + '\'' +
                ", pic='" + pic + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
