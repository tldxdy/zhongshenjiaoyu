package com.yunduancn.zhongshenjiaoyu.model;

/**
 * Created by Administrator on 2017/6/9.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 课程简介
 */
public class CourseshowModel implements Serializable{
    /**
     *
     *
     *  "msg": "success",
     "obj": {
     "id": "141",
     "coursename": "真像与真实：意义扭曲【高清】",
     "userIsStudent": false,
     "pic": "http://ceshi.yunduancn.cn/files/course/2017/05-25/1054179bd076279127.jpg?7.5.12",
     "introduce": "<p>真像与真实：意义扭曲【高清】</p>\n",
     "teachers": [
     {
     "id": "1",
     "nickname": "admin",
     "title": "讲师",
     "avatar": "http://ceshi.yunduancn.cn/files/user/2017/04-25/0852375e26fd121127.png?7.5.12"
     }
     ],
     "nextLearnLesson": null,
     "items": [
     {
     "lessonId": "988",
     "courseId": "141",
     "title": "真像与真实：意义扭曲【高清】",
     "length": "05:42"
     }
     ]
     }
     *
     *
     */

    private String id;
    private String coursename;
    private boolean userIsStudent;
    private String pic;
    private String introduce;
    private ArrayList<LecturerModel> teachers;
    private NextLearnLessonModel nextLearnLesson;
    private ArrayList<CourseChapterModel> items;

    private String favorite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CourseChapterModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CourseChapterModel> items) {
        this.items = items;
    }

    public NextLearnLessonModel getNextLearnLesson() {
        return nextLearnLesson;
    }

    public void setNextLearnLesson(NextLearnLessonModel nextLearnLesson) {
        this.nextLearnLesson = nextLearnLesson;
    }

    public ArrayList<LecturerModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<LecturerModel> teachers) {
        this.teachers = teachers;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isUserIsStudent() {
        return userIsStudent;
    }

    public void setUserIsStudent(boolean userIsStudent) {
        this.userIsStudent = userIsStudent;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }


    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "CourseshowModel{" +
                "id='" + id + '\'' +
                ", coursename='" + coursename + '\'' +
                ", userIsStudent=" + userIsStudent +
                ", pic='" + pic + '\'' +
                ", introduce='" + introduce + '\'' +
                ", teachers=" + teachers +
                ", nextLearnLesson=" + nextLearnLesson +
                ", items=" + items +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}
