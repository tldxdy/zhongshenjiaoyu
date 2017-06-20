package com.yunduancn.zhongshenjiaoyu.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyCourseModel {

    /**
     *  "total": "4",
     "items": [
     */
    private int total;
    private List<MyCourseAndCollectionModel> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MyCourseAndCollectionModel> getItems() {
        return items;
    }

    public void setItems(List<MyCourseAndCollectionModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MyCourseModel{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }
}
