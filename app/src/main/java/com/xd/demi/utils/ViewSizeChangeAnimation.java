package com.xd.demi.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewSizeChangeAnimation extends Animation {
    int initialWidth;
    int targetWidth;
    View view;

    public ViewSizeChangeAnimation(View view, int targetWidth) {
        this.view = view;
        this.targetWidth = targetWidth;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().width = initialWidth + (int) ((targetWidth - initialWidth) * interpolatedTime);
        view.requestLayout();
    }


    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        this.initialWidth = width;
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}