package com.dx.demi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.dx.demi.R;
import com.dx.demi.bean.Line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by demi on 2017/3/14.
 */

public class NineSquareView extends GridView {
    private Paint mPaint;
    private static int SQUAREWIDRH = 120; //默认正方形的边长
    private float mSquarewidth = SQUAREWIDRH; //每个正方形的边长
    private float x, y, startX, startY;
    private Bitmap bitmap;
    private LinkedHashSet<Line> lines = new LinkedHashSet<Line>();

    public NineSquareView(Context context) {
        this(context, null);
    }

    public NineSquareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineSquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.point);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float a = 0;
        float b = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.drawBitmap(bitmap, mSquarewidth * (0.5f + i) - bitmap.getWidth() / 2, mSquarewidth * (0.5f + j) - bitmap.getWidth() / 2, mPaint);
            }
        }
        Iterator<Line> iterator = lines.iterator();
        while (iterator.hasNext()) {
            Line line = iterator.next();
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), mPaint);
        }
//        for (int k = 0; k < lines.size(); k++) {
//            canvas.drawLine(lines.get(k).getStartX(),lines.get(k).getStartY(),lines.get(k).getEndX(),lines.get(k).getEndY(),mPaint);
//        }
        //找到每一次的起点
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(startX - mSquarewidth * (0.5f + i)) < bitmap.getWidth() / 2 &&
                        Math.abs(startY - mSquarewidth * (0.5f + j)) < bitmap.getWidth() / 2) {
                    canvas.drawLine(mSquarewidth * (0.5f + i), mSquarewidth * (0.5f + j), x, y, mPaint);
                    a = mSquarewidth * (0.5f + i);
                    b = mSquarewidth * (0.5f + j);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(x - mSquarewidth * (0.5f + i)) < bitmap.getWidth() / 2 &&
                        Math.abs(y - mSquarewidth * (0.5f + j)) < bitmap.getWidth() / 2
                        ) {
                    startX = mSquarewidth * (0.5f + i);
                    startY = mSquarewidth * (0.5f + j);
                    Line line = new Line(a, b, mSquarewidth * (0.5f + i), mSquarewidth * (0.5f + j));
                    lines.add(line);
                    System.out.println(lines.size());
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (wideMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            width = wideSize;
        } else {
            width = (int) (mSquarewidth * 3 + getPaddingLeft() + getPaddingRight());
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = (int) (mSquarewidth * 3 + getPaddingTop() + getPaddingBottom());
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
        mSquarewidth = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 3);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = ev.getX();
                y = ev.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                x = 0;
                y = 0;
                startX = 0;
                startY = 0;
                lines.clear();
                invalidate();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
