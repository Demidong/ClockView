package com.dx.demi.bean;

import java.util.ArrayList;

/**
 * Created by demi on 2017/6/7.
 */

public class ResponseData<T> {
    private String ret;
    private String msg;
    private ArrayList<T>  info;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<T> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<T> info) {
        this.info = info;
    }
}
