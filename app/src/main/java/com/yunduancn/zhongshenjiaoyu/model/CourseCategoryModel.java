package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CourseCategoryModel implements Serializable{

    /**
     * {
     "id": "8",
     "classname": "青少年",
     "path": "teens",
     "pic": "http://edu.yunduancn.cn/images/category/t1.jpg"
     }
     */

    private String id;
    private String classname;
    private String path;
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "CourseCategoryModel{" +
                "id='" + id + '\'' +
                ", classname='" + classname + '\'' +
                ", path='" + path + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
