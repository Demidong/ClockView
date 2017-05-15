package com.dx.demi.bean;

import java.io.Serializable;

/**
 * Created by demi on 2017/4/6.
 */

public class Point  implements Serializable{
    private float x ;
    private float y ;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
