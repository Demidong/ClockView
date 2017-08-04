package com.dx.demi.bean;

import java.util.ArrayList;

/**
 * Created by demi on 2017/6/7.
 */

public class ResponseData<T> {
    private String ret;
    private String msg;
    private T  info;

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

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
