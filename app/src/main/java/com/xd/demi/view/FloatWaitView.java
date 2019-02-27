package com.xd.demi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xd.demi.R;

import java.util.ArrayList;

/**
 * Created by demi on 2019/2/18 下午5:42.
 */
public class FloatWaitView extends View {
    private int ovalNum = 4;
    private int color;  //圆球颜色
    private int duration;   //圆球从底部浮动到顶部的时长
    private float wideSpace; //圆球之间的间隔
    private float floatHight; //圆球浮动高度
    public float radius;
    public int bg;
    public boolean ovalCenter;
    private Paint mPaint;
    private CountDownTimer timer;
    private ArrayList<Oval> mOvals = new ArrayList<>();
    int position = 0;
    private boolean isUserStop = false;
    private boolean isMoving = false;

    public FloatWaitView(Context context) {
        this(context, null);
    }

    public FloatWaitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatWaitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatWaitView);
        color = a.getColor(R.styleable.FloatWaitView_fwv_color, Color.RED);
        floatHight = a.getDimension(R.styleable.FloatWaitView_fwv_floatHight, 40);
        wideSpace = a.getDimension(R.styleable.FloatWaitView_fwv_wideSpace, 40);
        radius = a.getDimension(R.styleable.FloatWaitView_fwv_radius, 20);
        ovalNum = a.getInteger(R.styleable.FloatWaitView_fwv_ovalNum, 4);
        bg = a.getInteger(R.styleable.FloatWaitView_fwv_bg, Color.TRANSPARENT);
        duration = a.getInteger(R.styleable.FloatWaitView_fwv_duration, 800);
        ovalCenter = a.getBoolean(R.styleable.FloatWaitView_fwv_ovalInHorizontalCenter, true);
        a.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 调用时刻：onCreate之后onDraw之前调用；view的大小发生改变就会调用该方法
        int measuredWidth = getMeasuredWidth();
        for (int i = 0; i < ovalNum; i++) {
            Oval oval = mOvals.get(i);
            if (ovalCenter)
                oval.x = (measuredWidth - (wideSpace + 2 * radius) * ovalNum) / 2 + (wideSpace / 2 + radius) * (2 * i + 1);
            else
                oval.x = getPaddingLeft() + (wideSpace / 2 + radius) * (2 * i + 1);
            oval.y = floatHight - radius;
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        for (int i = 0; i < ovalNum; i++) {
            Oval oval = new Oval();
            oval.mAnimator = createFloatAnimation(i);
            mOvals.add(oval);
        }
    }

    private void createTimer() {
        timer = new CountDownTimer(duration, duration / (ovalNum + 1)) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (position < ovalNum) {
                    Oval oval = mOvals.get(position++);
                    oval.mAnimator.start();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    public void startMoving() {
        if (isMoving || getVisibility() == INVISIBLE) {
            return;
        }
        isMoving = true;
        isUserStop = false;
        createTimer();
    }

    public void stopMoving() {
        isUserStop = true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(bg);
        canvas.translate(0, getHeight() / 2 - floatHight / 2);
        canvas.drawRect(0, 0, getWidth(), floatHight, mPaint);
        mPaint.setColor(color);
        for (int i = 0; i < ovalNum; i++) {
            Oval oval = mOvals.get(i);
            canvas.drawCircle(oval.x, oval.y, radius, mPaint);
        }

    }

    private ValueAnimator createFloatAnimation(final int pos) {
        ValueAnimator animator = ValueAnimator.ofFloat(floatHight - radius, radius, floatHight - radius);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Oval oval = mOvals.get(pos);
                if (ovalCenter)
                    oval.x = (getWidth() - (wideSpace + 2 * radius) * ovalNum) / 2 + (wideSpace / 2 + radius) * (2 * pos + 1);
                else
                    oval.x = getPaddingLeft() + (wideSpace / 2 + radius) * (2 * pos + 1);
                oval.y = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (pos == ovalNum - 1) {
                    isMoving = false;
                    position = 0;
                    if (!isUserStop) {
                        createTimer();
                    }
                }
            }

        });
        return animator;
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isUserStop = true;
        if(timer != null){
            timer.cancel();
        }
        for (int i = 0; i < mOvals.size(); i++) {
            mOvals.get(i).mAnimator.cancel();
            mOvals.get(i).mAnimator.removeAllUpdateListeners();
        }
        mOvals.clear();
    }

    class Oval {
        public float x; //圆心 x坐标
        public float y;//圆心 y坐标
        public ValueAnimator mAnimator;//动画
    }

}
