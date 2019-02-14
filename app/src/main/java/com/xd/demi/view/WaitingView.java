package com.xd.demi.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xd.demi.R;

/**
 * Created by demi on 2018/12/6 下午5:39.
 */
public class WaitingView extends View {
    Paint mPaint = null;
    int bgColor = Color.TRANSPARENT;
    float x = 0, y = 0;
    float handX = 0, handY = 0;
    float value;
    Path path = new Path();
    Point mPoint = new Point(0,0);
    public WaitingView(Context context) {
        this(context, null);
    }

    public WaitingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
