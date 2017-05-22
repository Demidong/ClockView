package com.dx.demi.utils;

import android.graphics.RectF;

/**
 * Created by hexi on 15/8/27.
 */
public class ViewPortHandler {
    private float canvasWidth = 0f;
    private float canvasHeight = 0f;

    private RectF contentRect = new RectF();

    public void setCanvasDimens(float width, float height) {
        float offsetLeft = this.offsetLeft();
        float offsetTop = this.offsetTop();
        float offsetRight = this.offsetRight();
        float offsetBottom = this.offsetBottom();

        canvasWidth = width;
        canvasHeight = height;

        restrainViewPort(offsetLeft, offsetTop, offsetRight, offsetBottom);
    }

    public void restrainViewPort(float offsetLeft, float offsetTop, float offsetRight, float offsetBottom) {
        contentRect.set(offsetLeft, offsetTop, canvasWidth - offsetRight, canvasHeight - offsetBottom);
    }

    public float offsetLeft() {
        return contentRect.left;
    }

    public float offsetTop() {
        return contentRect.top;
    }

    public float offsetRight() {
        return canvasWidth - contentRect.right;
    }

    public float offsetBottom() {
        return canvasHeight - contentRect.bottom;
    }

    public float contentWidth() {
        return contentRect.width();
    }

    public float contentHeight() {
        return contentRect.height();
    }

    public float getCanvasHeight() {
        return canvasHeight;
    }

    public RectF getContentRect() {
        return contentRect;
    }
}
