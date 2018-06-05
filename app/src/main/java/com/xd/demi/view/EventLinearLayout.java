package com.xd.demi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by demi on 16/12/8.
 */

public class EventLinearLayout extends LinearLayout {
    public EventLinearLayout(Context context) {
        super(context);
    }

    public EventLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        System.out.println("LinearLayout onInterceptTouchEvent..." + event.getAction());
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("LinearLayout dispatchTouchEvent..." + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("LinearLayout onTouchEvent..." + event.getAction());
        return super.onTouchEvent(event);
    }
}
