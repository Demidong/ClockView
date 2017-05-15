package com.dx.demi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by demi on 16/11/29.
 */

public class CoordinateView extends View {

    private Paint mPaint = new Paint();
    public CoordinateView(Context context) {
        this(context,null);
    }

    public CoordinateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawPoint(0,0,mPaint);
        canvas.drawPoints(new float[]{getWidth()/2*0.8f,0,
                -getWidth()/2*0.8f,0,
        0,getHeight()/2*0.8f,
        0,-getHeight()/2*0.8f},mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(-getWidth()/2*0.8f,0,getWidth()/2*0.8f,0,mPaint);
        canvas.drawLine(0,-getHeight()/2*0.8f,0,getHeight()/2*0.8f,mPaint);
        mPaint.setColor(Color.BLACK);
        //绘制X轴箭头
        canvas.drawLines(new float[]{
                getWidth()/2*0.8f,0,getWidth()/2*0.8f*0.95f,-getWidth()/2*0.8f*0.05f,
                getWidth()/2*0.8f,0,getWidth()/2*0.8f*0.95f,getWidth()/2*0.8f*0.05f
        },mPaint);
//绘制Y轴箭头
        canvas.drawLines(new float[]{
                0,getHeight()/2*0.8f,getWidth()/2*0.8f*0.05f,getHeight()/2*0.8f-getWidth()/2*0.8f*0.05f,
                0,getHeight()/2*0.8f,-getWidth()/2*0.8f*0.05f,getHeight()/2*0.8f-getWidth()/2*0.8f*0.05f,
        },mPaint);
        Path path = new Path();
//        Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.mipmap.single);
//        Matrix matrix =new Matrix();
//        System.out.println(bitmap.getWidth()+"//"+bitmap.getHeight());
//        matrix.postScale(0.5f,0.5f);
//        Bitmap b = bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
//        path.addCircle(0,0,Math.min(bitmap.getWidth()/4,bitmap.getHeight()/4), Path.Direction.CW);
//        canvas.clipPath(path, Region.Op.INTERSECT);
//        canvas.drawBitmap(b,-bitmap.getWidth()/4,-bitmap.getHeight()/4,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        Path path2 = new Path();
        path1.moveTo(0,0);
        path1.arcTo(new RectF(-200,-200,200,200),240,60,false);
        path1.lineTo(0,0);
        path2.moveTo(0,0);
        path2.arcTo(new RectF(-300,-300,300,300),240,60,false);
        path2.lineTo(0,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            path.op(path1,path2, Path.Op.XOR);
        }
        canvas.drawPath(path,mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
