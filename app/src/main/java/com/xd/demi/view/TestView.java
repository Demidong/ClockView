package com.xd.demi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by demi on 2019/3/4 下午4:12.
 */
public class TestView extends View {
    Paint mPaint;
    Path  mPath;
    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addCircle(400,400,100, Path.Direction.CW);
        mPath.addCircle(500,400,100, Path.Direction.CW);
        mPath.addCircle(450,500,100, Path.Direction.CW);
//        canvas.save();
        canvas.rotate(20);
        canvas.drawRect(400,400,500,500,mPaint);
//        canvas.restore();
        canvas.drawPath(mPath,mPaint);
    }
}
