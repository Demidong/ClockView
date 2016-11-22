package com.dx.demi;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by demi on 16/11/17.
 */

public class PieChatView extends View {
    private int sum = 0;
    /**
     * 存放事物的品种与其对应的数量
     */
    private Map kindsMap = new LinkedHashMap<String, Integer>();
    private ArrayList<Integer> colors = new ArrayList<>();

    private Paint mPaint;//饼状画笔
    private Paint mTextPaint; // 文字画笔
    private static final int DEFAULT_RADIUS = 200;
    private int mRadius = DEFAULT_RADIUS; //时钟的半径
    private float animatedValue;
    private RectF oval;
    /**
     * 格式化小数点
     */
    private DecimalFormat dff;
    public PieChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTextPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(15);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        kindsMap.put("苹果", 10);
        kindsMap.put("梨子", 30);
        kindsMap.put("香蕉", 10);
        kindsMap.put("葡萄", 30);
        kindsMap.put("哈密瓜", 10);
        kindsMap.put("猕猴桃",30);
        kindsMap.put("草莓", 10);
        kindsMap.put("橙子", 30);
        kindsMap.put("火龙果", 10);
        kindsMap.put("椰子", 20);
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(5000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
        sum = getSum(kindsMap);
        for(int i = 0; i<10; i++){
            int random =  new Random().nextInt(100);
            int  color =  Color.rgb(random + 5 * i,random * i,random - 5 * i);
            if(!colors.contains(color)){
                colors.add(color) ;
            }
        }
        dff =new DecimalFormat("0.0");
    }

    public PieChatView(Context context) {
        this(context, null, 0);

    }

    public PieChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
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
            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
        oval = new RectF(-mRadius, -mRadius, mRadius, mRadius);
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        mCanvas.translate((getWidth()+ getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom())/ 2);

        paintPie(mCanvas, kindsMap);

    }


    private void paintPie(final Canvas mCanvas, Map<String, Integer> map) {
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        int i = 0;
        float currentAngle = 0.0f;
        while (iterator.hasNext()) {
            String kinds = iterator.next();
            int num = map.get(kinds);
            float needDrawAngle = num * 1.0f / sum * 360;
            String drawAngle = dff.format(needDrawAngle/360*100);
            kinds =kinds +","+drawAngle+"%";
            float textAngle = needDrawAngle / 2 + currentAngle;
            if (animatedValue - currentAngle > 0) {
                mPaint.setColor(colors.get(i));
                mCanvas.drawArc(oval, currentAngle, animatedValue - currentAngle, true, mPaint);
                System.out.println("draw---->currentAngle: " + currentAngle + "needDrawAngle:" + needDrawAngle + "animatedValue - currentAngle:" + (animatedValue - currentAngle));

                drawText(mCanvas,textAngle,kinds,needDrawAngle);
            }
            System.out.println("currentAngle: " + currentAngle + "needDrawAngle:" + needDrawAngle + "animatedValue:" + animatedValue);
            currentAngle = currentAngle + needDrawAngle;
            i++;
        }
    }

   private void  drawText(Canvas mCanvas,float textAngle,String kinds,float needDrawAngle){
       Rect rect = new Rect();
       mTextPaint.getTextBounds(kinds, 0, kinds.length(), rect);
       if (textAngle >= 0 && textAngle <= 90) {
           if (needDrawAngle < 30) {
               mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(textAngle))), (float) (mRadius * 1.2 * Math.sin(Math.toRadians(textAngle))), mTextPaint);
           } else {
               mCanvas.drawText(kinds, (float) (mRadius / 2 * Math.cos(Math.toRadians(textAngle))), (float) (mRadius / 2 * Math.sin(Math.toRadians(textAngle))), mTextPaint);
           }
       } else if (textAngle > 90 && textAngle <= 180) {
           if (needDrawAngle < 30) {
               mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(180 -textAngle))), (float) (mRadius * 1.2 * Math.sin(Math.toRadians(180 -textAngle))), mTextPaint);
           } else {
               mCanvas.drawText(kinds, (float) (-mRadius / 2 * Math.cos(Math.toRadians(180 - textAngle))), (float) (mRadius / 2 * Math.sin(Math.toRadians(180 - textAngle))), mTextPaint);
           }
       } else if (textAngle > 180 && textAngle <= 270) {
           if (needDrawAngle < 30) {
               mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(textAngle-180))), (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(textAngle-180))), mTextPaint);
           } else {
               mCanvas.drawText(kinds, (float) (-mRadius / 2 * Math.cos(Math.toRadians(textAngle - 180))), (float) (-mRadius / 2 * Math.sin(Math.toRadians(textAngle - 180))), mTextPaint);
           }
       } else {
           if (needDrawAngle < 30) {
               mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(360-textAngle))), (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(360-textAngle))), mTextPaint);
           } else {
               mCanvas.drawText(kinds, (float) (mRadius / 2 * Math.cos(Math.toRadians(360 - textAngle))), (float) (-mRadius / 2 * Math.sin(Math.toRadians(360 - textAngle))), mTextPaint);
           }
       }

   }
    private int getSum(Map<String, Integer> map) {
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        int sum = 0;

        while (iterator.hasNext()) {
            String kinds = iterator.next();
            int num = map.get(kinds);
            sum = sum + num;
        }
        return sum;
    }

}
