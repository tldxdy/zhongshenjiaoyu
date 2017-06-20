package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16.
 */

public class ArticleCategoryModel implements Serializable{
    /**
     *{
     "name": "全部",
     "code": ""
     }
     */
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ArticleCategoryModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
