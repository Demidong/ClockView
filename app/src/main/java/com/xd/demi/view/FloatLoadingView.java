package com.xd.demi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xd.demi.R;

/**
 * Created by demi on 2019/2/18 下午5:42.
 */
public class FloatLoadingView extends View {
    private int ovalNum = 4;
    private int color;  //圆球颜色
    private int duration;   //圆球从底部浮动到顶部的时长
    private float wideSpace; //圆球之间的间隔
    private float floatHight; //圆球浮动高度
    public float radius;
    public boolean ovalCenter;
    private Paint mPaint;
    private CountDownTimer timer;
    private FixedOval[] mOvals;
    private MoveOval mMoveOval;
    int currentPosition = 0;
    private ValueAnimator valueAnimator;
    private boolean isMoving;
    private boolean isUserStop;

    public FloatLoadingView(Context context) {
        this(context, null);
    }

    public FloatLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatLoadingView);
        color = a.getColor(R.styleable.FloatLoadingView_flv_color, Color.RED);
        floatHight = a.getDimension(R.styleable.FloatLoadingView_flv_floatHight, 40);
        wideSpace = a.getDimension(R.styleable.FloatLoadingView_flv_wideSpace, 40);
        radius = a.getDimension(R.styleable.FloatLoadingView_flv_radius, 20);
        ovalNum = a.getInteger(R.styleable.FloatLoadingView_flv_ovalNum, 4);
        duration = a.getInteger(R.styleable.FloatLoadingView_flv_duration, 800);
        ovalCenter = a.getBoolean(R.styleable.FloatLoadingView_flv_ovalInHorizontalCenter, true);
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        initOval();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 调用时刻：onCreate之后onDraw之前调用；view的大小发生改变就会调用该方法
        for (int i = 0; i < ovalNum; i++) {
            float x = (getMeasuredWidth() - (wideSpace + 2 * radius) * ovalNum) / 2 + radius;
            float y = floatHight - radius;
            if (i == 0) {
                mOvals[i].point.set(x, y);
            } else {
                mOvals[i].point.set(mOvals[i - 1].point);
                mOvals[i].point.offset(2 * radius + wideSpace, 0);
            }
        }
        mMoveOval.point.set(mOvals[currentPosition].point);
    }

    private void initOval() {
        mOvals = new FixedOval[ovalNum];
        for (int i = 0; i < ovalNum; i++) {
            mOvals[i] = new FixedOval();
            mOvals[i].index = i;
            mOvals[i].point = new PointF();
            mOvals[i].isShow = true;
        }
        mMoveOval = new MoveOval();
        mMoveOval.index = currentPosition;
        mMoveOval.point = new PointF();
        relate_Oval(mOvals);
    }

    private void relate_Oval(FixedOval[] ovals) {
        for (int i = 0; i < ovalNum; i++) {
            if (i == ovalNum - 1) {
                ovals[i].next = mOvals[0];
            } else {
                ovals[i].next = mOvals[i + 1];
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, getHeight() / 2 - floatHight / 2);
        mPaint.setColor(color);
        for (int i = 0; i < ovalNum; i++) {
            FixedOval oval = mOvals[i];
            if (oval.isShow) {
                canvas.drawCircle(oval.point.x, oval.point.y, radius, mPaint);
            }
        }
        canvas.drawCircle(mMoveOval.point.x, mMoveOval.point.y, radius, mPaint);
    }

    private void createFloatAnimator() {
        valueAnimator = ValueAnimator.ofFloat(floatHight - radius, radius, floatHight - radius);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mMoveOval.point.y = value;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mMoveOval.point.set(mOvals[currentPosition].point);
                mMoveOval.index = mOvals[currentPosition].index;
                mOvals[currentPosition].isShow = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMoving = false;
                mOvals[currentPosition].isShow = true;
                currentPosition = mOvals[currentPosition].next.index;
                if (!isUserStop) {
                    startMoving();
                }
            }

        });
        valueAnimator.start();
    }

    public void startMoving() {
        if (isMoving || getVisibility() == INVISIBLE) {
            return;
        }
        isMoving = true;
        isUserStop = false;
        createFloatAnimator();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWideSize, resultWideMode, resultHighSize, resultHighMode;
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int highMode = MeasureSpec.getMode(heightMeasureSpec);
        int highSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wideMode == MeasureSpec.EXACTLY) {
            resultWideMode = MeasureSpec.EXACTLY;
            resultWideSize = wideSize + getPaddingLeft() + getPaddingRight();
        } else {
            resultWideMode = MeasureSpec.AT_MOST;
            resultWideSize = (int) ((wideSpace + 2 * radius) * ovalNum) + getPaddingLeft() + getPaddingRight();
        }
        if (highMode == MeasureSpec.EXACTLY) {
            resultHighMode = MeasureSpec.EXACTLY;
            resultHighSize = highSize + getPaddingTop() + getPaddingBottom();
        } else {
            resultHighMode = MeasureSpec.AT_MOST;
            resultHighSize = (int) (floatHight + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(resultWideSize, resultWideMode), MeasureSpec.makeMeasureSpec(resultHighSize, resultHighMode));
    }

    public void stopMoving() {
        isUserStop = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(valueAnimator != null){
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
    }

    private class MoveOval {
        PointF point;
        int index;
    }

    private class FixedOval {
        PointF point;

        int index;
        FixedOval next;
        boolean isShow;
    }
}
