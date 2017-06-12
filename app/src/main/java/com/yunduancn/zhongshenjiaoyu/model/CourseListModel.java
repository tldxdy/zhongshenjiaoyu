package com.yunduancn.zhongshenjiaoyu.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class CourseListModel {

    private int total;
    private List<Coursesmodel> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Coursesmodel> getItems() {
        return items;
    }

    public void setItems(List<Coursesmodel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CourseListModel{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }
}
