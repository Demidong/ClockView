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

public class NineSquareView extends View {
    private Paint pointPaint;
    private Paint linePaint;
    private Path path;
    private static int SQUAREWIDRH = 400; //默认正方形的边长
    private float mSquarewidth = SQUAREWIDRH; //每个正方形的边长
    private float x, y, startX, startY;
    private LinkedHashMap<String,Point> points = new LinkedHashMap<>();
    private OnFinishGestureListener finishGestureListener ;

    public NineSquareView(Context context) {
        this(context, null);
    }

    public NineSquareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineSquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.CYAN);
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.parseColor("#cbd0de"));
        pointPaint.setStrokeWidth(40);
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
        path =new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float a = 0;
        float b = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pointPaint.setColor(Color.parseColor("#cbd0de"));
                canvas.drawPoint(mSquarewidth * (0.5f + i),mSquarewidth * (0.5f + j),pointPaint);
            }
        }

        Collection<Point> collection = points.values();
        Iterator<Point> iterator = collection.iterator();
        if(iterator.hasNext()){
            Point point = iterator.next();
            drawCyanPoint(canvas,point);
            System.out.println("moveTo:"+point.getX()+"===="+point.getY());
            path.moveTo(point.getX(),point.getY());
        }
        while (iterator.hasNext()) {
            Point point = iterator.next();
            drawCyanPoint(canvas,point);
            System.out.println("lineTo:"+point.getX()+"===="+point.getY());
            path.lineTo(point.getX(),point.getY());
        }
        canvas.drawPath(path,linePaint);
        path.reset();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(startX - mSquarewidth * (0.5f + i)) < mSquarewidth * 0.3f &&
                        Math.abs(startY - mSquarewidth * (0.5f + j)) < mSquarewidth * 0.3f) {
                    path.moveTo(mSquarewidth * (0.5f + i), mSquarewidth * (0.5f + j));
                    path.lineTo(x, y);
                    canvas.drawPath(path,linePaint);
                    path.reset();
                    a = mSquarewidth * (0.5f + i);
                    b = mSquarewidth * (0.5f + j);
                    Point point =new Point(a,b);
                    points.put(i+":"+j,point);
                    System.out.println(points.size());
                    System.out.println(i+"//"+j);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(x - mSquarewidth * (0.5f + i)) < mSquarewidth * 0.3f &&
                        Math.abs(y - mSquarewidth * (0.5f + j)) <mSquarewidth * 0.3f
                        ) {
                    Iterator<Point> iterator2 = collection.iterator();
                    while(iterator2.hasNext()){
                        Point point = iterator2.next();
                       if(mSquarewidth * (0.5f + i)==point.getX() && mSquarewidth * (0.5f + j)==point.getY()){
                              return;
                       }
                    }
                    startX = mSquarewidth * (0.5f + i);
                    startY = mSquarewidth * (0.5f + j);

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
                finishGestureListener.onfinish(points);
                points.clear();
                invalidate();
                break;
        }
        return true;
    }
    public interface OnFinishGestureListener {
       void onfinish(LinkedHashMap<String,Point> points);

    }

    //绘制手指划到的那个点,点外加上一层圈。
    public void drawCyanPoint(Canvas canvas, Point point){
        String s =getKey(point);
        String [] strings =  s.split(":");
        int i= Integer.parseInt(strings[0]);
        int j=Integer.parseInt(strings[1]);
        pointPaint.setColor(Color.CYAN);
        canvas.drawPoint(mSquarewidth * (0.5f + i),mSquarewidth * (0.5f + j),pointPaint);
        canvas.drawCircle(mSquarewidth * (0.5f + i),mSquarewidth * (0.5f + j),mSquarewidth * 0.3f,linePaint);
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
    public void setOnFinishGestureListener(OnFinishGestureListener finishGestureListener){
        this.finishGestureListener =finishGestureListener ;
    }
}
