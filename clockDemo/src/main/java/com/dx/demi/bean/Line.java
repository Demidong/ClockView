package com.dx.demi.bean;

/**
 * Created by demi on 2017/3/15.
 */

public class Line{
    private float endX ;
    private float endY ;
    private float startX ;
    private float startY ;


    public Line() {

    }

    public Line(float endX, float endY, float startX, float startY) {
        this.endX = endX;
        this.endY = endY;
        this.startX = startX;
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    @Override
    public boolean equals(Object o) {
        if(((Line)o).startX == this.startX&&((Line)o).startY == this.startY){
            return  true ;
        }
        return false;
    }
}
