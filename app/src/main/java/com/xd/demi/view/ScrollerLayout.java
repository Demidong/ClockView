package com.xd.demi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by demi on 2018/7/4 下午2:24.
 */
public class ScrollerLayout extends LinearLayout {
    private Scroller scroller;
    private GestureDetector mGestureDetector ;
    private int x ;
    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context,new GestureListenerImpl());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                scroller.startScroll(scroller.getFinalX(),scroller.getFinalY(),0, -x);
                invalidate();
                x = 0;
                return super.onTouchEvent(event);
            }else{
                return mGestureDetector.onTouchEvent(event);
            }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
    private class GestureListenerImpl implements GestureDetector.OnGestureListener {
        //触摸屏幕时均会调用该方法
        @Override
        public boolean onDown(MotionEvent e) {
            System.out.println("---> 手势中的onDown方法");
            return true;
        }

        //手指在屏幕上拖动时会调用该方法
        @Override
        public boolean onFling(MotionEvent e1,MotionEvent e2, float velocityX,float velocityY) {
            System.out.println("---> 手势中的onFling方法");
            return false;
        }

        //手指长按屏幕时均会调用该方法
        @Override
        public void onLongPress(MotionEvent e) {
            System.out.println("---> 手势中的onLongPress方法");
        }

        //手指在屏幕上滚动时会调用该方法
        @Override
        public boolean onScroll(MotionEvent e1,MotionEvent e2, float distanceX,float distanceY) {
            System.out.println("---> 手势中的onScroll方法 distanceY："+distanceY);
            x += (int) distanceY/2;
            scroller.startScroll(scroller.getFinalX(),scroller.getFinalY(),0, (int) distanceY/2);
            invalidate();
            return false;
        }

        //手指在屏幕上按下,且未移动和松开时调用该方法
        @Override
        public void onShowPress(MotionEvent e) {
            System.out.println("---> 手势中的onShowPress方法");
        }

        //轻击屏幕时调用该方法
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            System.out.println("---> 手势中的onSingleTapUp方法");
            return false;
        }
    }
}
