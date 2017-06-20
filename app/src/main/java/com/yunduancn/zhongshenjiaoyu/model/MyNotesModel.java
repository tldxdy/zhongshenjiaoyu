package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MyNotesModel implements Serializable{

    /**
     *  "total": "1",
     "items": [
     */

    private Integer total;
    private List<MyNotesListModel> items;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<MyNotesListModel> getItems() {
        return items;
    }

    public void setItems(List<MyNotesListModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MyNotesModel{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }
}
