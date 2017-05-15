package com.dx.demi.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by demi on 2017/2/13.
 */

public class MyViewPager extends ViewPager {
    private float x = 0,y=0;
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                x=ev.getX();
                y=ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getX()-x)>Math.abs(ev.getY()-y)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                    break;
        }

        return super.onTouchEvent(ev);
    }
}
