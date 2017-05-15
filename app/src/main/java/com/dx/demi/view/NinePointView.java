package com.dx.demi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.dx.demi.bean.Point;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by demi on 2017/3/14.
 */

public class NinePointView extends View {
    private Paint pointPaint;
    private LinkedHashMap<String,Point> points = new LinkedHashMap<>();
    private static int SQUAREWIDRH = 30; //默认正方形的边长
    private float mSquarewidth = SQUAREWIDRH; //每个正方形的边长

    public NinePointView(Context context) {
        this(context, null);
    }

    public NinePointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.parseColor("#cbd0de"));
        pointPaint.setStrokeWidth(20);
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
    }
  public void setHightLightPoints(LinkedHashMap<String,Point> points){
      this.points.putAll(points);
      postInvalidate();
  }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pointPaint.setColor(Color.parseColor("#cbd0de"));
                canvas.drawPoint(mSquarewidth * (0.5f + i),mSquarewidth * (0.5f + j),pointPaint);
            }
        }
        Collection<Point> collection = points.values();
        Iterator<Point> iterator = collection.iterator();
        while(iterator.hasNext()){
            Point point = iterator.next();
            drawCyanPoint(canvas,point);
        }
        points.clear();
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
    //绘制手指划到的那个点,点外加上一层圈。
    public void drawCyanPoint(Canvas canvas, Point point){
        String s =getKey(point);
        String [] strings =  s.split(":");
        int i= Integer.parseInt(strings[0]);
        int j=Integer.parseInt(strings[1]);
        pointPaint.setColor(Color.CYAN);
        canvas.drawPoint(mSquarewidth * (0.5f + i),mSquarewidth * (0.5f + j),pointPaint);
    }
    //根据value取key值
    public  String getKey(Point value)//根据字符得到对应的编码
    {
        String key = "";
        Set<Map.Entry<String, Point>> set = points.entrySet();
        for(Map.Entry<String, Point> entry : set){
            if(entry.getValue().equals(value)){
                key = entry.getKey();
                break;
            }
        }
        return key;
    }
    public void clearPoints(){
        points.clear();
        postInvalidate();
    }
}
