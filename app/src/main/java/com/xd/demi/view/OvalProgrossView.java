package com.xd.demi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xd.demi.R;

import java.text.DecimalFormat;

/**
 * Created by demi on 16/7/15.
 */
public class OvalProgrossView extends View {
    private static final int DEFAULT_RADIUS = 150;
    /**
     * 圆圈颜色
     *
     * @param context
     */
    private int mOvalColor;
    /**
     * 进度颜色
     *
     * @param context
     */
    private int mProgressColor;
    /**
     * 进度描述内容颜色
     *
     * @param context
     */
    private int mfixTextColor;

    /**
     * 圆圈宽度
     *
     * @param context
     */
    private int mOvalWidth;


    /**
     * 圆圈最终进度
     *
     * @param context
     */
    private float mOvalProgress;
    /**
     * 圆圈中间变动进度文字大小
     *
     * @param context
     */
    private int mProgressTextSize = sp2px(50);
    /**
     * 圆圈中间固定文字尺寸大小
     *
     * @param context
     */
    private int mFixTextSize = sp2px(40);
    /**
     * 圆圈中间变动进度
     *
     * @param context
     */
    private String mProgressText = "0.0";
    /**
     * 圆圈中间固定文字
     *
     * @param context
     */
    private String mFixText = "盈利指数";
    /**
     * 圆圈当前进度
     *
     * @param context
     */
    private float mProgress;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 是否已到达最终进度
     *
     * @param context
     */
    private boolean isStop = false;
    /**
     * 格式化小数点
     */
    private DecimalFormat dff;
    /**
     * 圆心
     */
    private int centreX, centreY;
    /**
     * 半径
     */
    private int radius = DEFAULT_RADIUS;

    public OvalProgrossView(Context context) {
        this(context, null);
    }

    public OvalProgrossView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvalProgrossView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OvalProgressView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.OvalProgressView_ovalColor:
                    mOvalColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.OvalProgressView_fixTextColor:
                    mfixTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.OvalProgressView_progressTextColor:
                    mProgressColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.OvalProgressView_progressTextSize:
                    mProgressTextSize = a.getDimensionPixelSize(attr, sp2px(50));
                    break;
                case R.styleable.OvalProgressView_fixTextSize:
                    mFixTextSize = a.getDimensionPixelSize(attr, sp2px(40));
                    break;
                case R.styleable.OvalProgressView_ovalWidth:
                    mOvalWidth = a.getDimensionPixelSize(attr, sp2px(5));
                    break;
                case R.styleable.OvalProgressView_progressText:
                    mProgressText = a.getString(attr);
                    mOvalProgress = Float.valueOf(mProgressText);
                    break;
                case R.styleable.OvalProgressView_fixText:
                    mFixText = a.getString(attr);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        dff = new DecimalFormat("0.0");

    }

    public void setmOvalProgress(float mOvalProgress) {
        this.mOvalProgress = mOvalProgress;
        isStop = false;
        postInvalidate();
        mProgress = 0;
        if (mOvalProgress >= 0) {
            mOvalColor = getResources().getColor(R.color.red);
        } else {
            mOvalColor = getResources().getColor(R.color.green);
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
            width = radius * 2 + getPaddingLeft() + getPaddingRight();
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = radius * 2 + getPaddingLeft() + getPaddingRight();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
        centreX = width / 2; // 获取圆心的x坐标
        centreY = height / 2; // 获取圆心的y坐标
        radius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2) - mOvalWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mOvalProgress == 0) {
            isStop = true;
            mProgressText = dff.format(mProgress);
        } else if (mOvalProgress < 0) {
            mProgressText = dff.format(-mProgress);
            mProgress = mProgress + 1.1f;
            if (mProgress >= -mOvalProgress || getVisibility() == View.INVISIBLE || mProgress >= 100) {
                isStop = true;
                mProgressText = dff.format(mOvalProgress);//当最后一次画的时候,显示最终指定的值.
                mProgress = -mOvalProgress;
            }
        } else {
            mProgressText = dff.format(mProgress);
            mProgress = mProgress + 1.1f;
            if (mProgress >= mOvalProgress || getVisibility() == View.INVISIBLE || mProgress >= 100) {
                isStop = true;
                mProgressText = dff.format(mOvalProgress);//当最后一次画的时候,显示最终指定的值.
                mProgress = mOvalProgress;
            }
        }
        paintCircle(canvas);
        paintOval(canvas);
        paintText(canvas);
        if (!isStop) {
            postInvalidate();
        }
    }


    private void paintText(Canvas canvas) {
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setColor(mOvalColor);
        mPaint.setStyle(Paint.Style.FILL); // 设置实心
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(mProgressText, centreX, centreY, mPaint);

        Rect rect = new Rect();
        mPaint.getTextBounds(mFixText, 0, mFixText.length(), rect);
        mPaint.setTextSize(mFixTextSize);
        mPaint.setColor(mfixTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mFixText, centreX, centreY + rect.height() * 0.7f, mPaint);

    }

    public void paintCircle(Canvas canvas) {
        mPaint.setStrokeWidth(mOvalWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(Color.parseColor("#dddddd")); // 设置圆环的颜色
        RectF oval = new RectF(centreX - radius, centreY - radius, centreX + radius, centreY + radius);
        canvas.drawCircle(centreX, centreY, radius, mPaint);
    }

    public void paintOval(Canvas canvas) {
        mPaint.setStrokeWidth(mOvalWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(mOvalColor); // 设置圆环的颜色
        RectF oval = new RectF(centreX - radius, centreY - radius, centreX + radius, centreY + radius);
        canvas.drawArc(oval, 270, (float) (-mProgress * 3.6), false, mPaint); // 根据进度画圆弧
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }


    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }
}
