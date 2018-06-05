package com.xd.demi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tong on 17/5/5.
 */
public class ListInfo<T> implements Serializable {
    private int total;
    private String type;
    private ArrayList<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
