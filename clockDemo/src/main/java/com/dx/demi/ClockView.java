package com.dx.demi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by demi on 16/11/10.
 */

public class ClockView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private static final int DEFAULT_RADIUS = 200;
    private Paint mPaint;//园和刻度的画笔
    private Paint mPointPaint; // 指针画笔
    private int mCanvasWidth, mCanvasHeight;//画布的宽高
    private int mRadius = DEFAULT_RADIUS; //时钟的半径
    private int mSecondPointLength; //秒针长度
    private int mMinutePointLength;//分针长度
    private int mHourPointLength; //时针长度
    private int mHourDegreeLength; //时刻度
    private int mSecondDegreeLength; //秒刻度
    private int mHour, mMinute, mSecond; //时分秒
    private SurfaceHolder mHolder;
    private Thread mThread;
    private boolean flag;

    public ClockView(Context context) {
        this(context, null, 0);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mSecond = Calendar.getInstance().get(Calendar.SECOND);

        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new Thread(this);
        mPaint = new Paint();
        mPointPaint = new Paint();

        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointPaint.setTextSize(30);
        mPointPaint.setTextAlign(Paint.Align.CENTER);

        setFocusable(true);
        setFocusableInTouchMode(true);


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
            height = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(mCanvasWidth = width + 4, mCanvasHeight = height + 4);
        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);

        caculateLength();

    }

    private void caculateLength() {
        mHourDegreeLength = (int) (mRadius * 1.0f / 7);
        mSecondDegreeLength = mHourDegreeLength / 2;
        // 时针长度为半径一半
        // 指针长度比 hour : minute : second = 1 : 1.25 : 1.5
        mHourPointLength = (int) (mRadius * 1.0 / 2);
        mMinutePointLength = (int) (mHourPointLength * 1.25f);
        mSecondPointLength = (int) (mHourPointLength * 1.5f);
    }

    private void draw() {
        Canvas mCanvas = null;
        try {
            mCanvas = mHolder.lockCanvas(); // 得到画布
            if (mCanvas != null) {
                // 在这里绘制内容
                drawContent(mCanvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                // 提交画布，否则什么都看不见
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawContent(Canvas mCanvas) {
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mSecond = Calendar.getInstance().get(Calendar.SECOND);
        // 1.将坐标系原点移至去除内边距后的画布中心
        // 默认在画布左上角，这样做是为了更方便的绘制

        mCanvas.drawColor(Color.WHITE);
        mCanvas.translate(mCanvasWidth * 1.0f / 2 - (getPaddingLeft() + getPaddingRight())/2, mCanvasHeight * 1.0f / 2 -( getPaddingTop() - getPaddingBottom())/2);
// 2.绘制圆盘
        mPaint.setStrokeWidth(2f); // 画笔设置2个像素的宽度
        mCanvas.drawCircle(0, 0, mRadius, mPaint); // 到这一步就能知道第一步的好处了，否则害的去计算园的中心点坐标
// 3.绘制时刻度
        for (int i = 0; i < 12; i++) {
            mCanvas.drawLine(0, mRadius, 0, mRadius - mHourDegreeLength, mPaint);
            mCanvas.rotate(30); // 360°平均分成12份，每份30°
        }
// 4.绘制秒刻度
        mPaint.setStrokeWidth(1.5f);
        for (int i = 0; i < 60; i++) {
            //时刻度绘制过的区域不在绘制
            if (i % 5 != 0) {
                mCanvas.drawLine(0, mRadius, 0, mRadius - mSecondDegreeLength, mPaint);
            }
            mCanvas.rotate(6); // 360°平均分成60份，每份6°
        }
// 5.绘制数字
        mPointPaint.setColor(Color.BLACK);
        for (int i = 0; i < 12; i++) {
            String number = 6 + i < 12 ? String.valueOf(6 + i) : (6 + i) > 12
                    ? String.valueOf(i - 6) : "12";
            mCanvas.drawText(number, 0, mRadius * 5.5f / 7, mPointPaint);
            mCanvas.rotate(30);
        }
// 6.绘制上下午
        mCanvas.drawText(mHour < 12 ? "AM" : "PM", 0, mRadius * 1.5f / 4, mPointPaint);
        mCanvas.drawText(mHour+":"+mMinute+":"+mSecond, 0, mRadius * 1.3f, mPointPaint);
// 7.绘制时针
        Path path = new Path();
        path.moveTo(0, 0);
        int[] hourPointerCoordinates = getPointerCoordinates(mHourPointLength);
        path.lineTo(hourPointerCoordinates[0], hourPointerCoordinates[1]);
        path.lineTo(hourPointerCoordinates[2], hourPointerCoordinates[3]);
        path.lineTo(hourPointerCoordinates[4], hourPointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mHour % 12 * 30 + mMinute * 1.0f / 60 * 30);
        mCanvas.drawPath(path, mPointPaint);
        mCanvas.restore();
// 8.绘制分针
        path.reset();
        path.moveTo(0, 0);
        int[] minutePointerCoordinates = getPointerCoordinates(mMinutePointLength);
        path.lineTo(minutePointerCoordinates[0], minutePointerCoordinates[1]);
        path.lineTo(minutePointerCoordinates[2], minutePointerCoordinates[3]);
        path.lineTo(minutePointerCoordinates[4], minutePointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mMinute * 6);
        mCanvas.drawPath(path, mPointPaint);
        mCanvas.restore();
// 9.绘制秒针
        mPointPaint.setColor(Color.RED);
        path.reset();
        path.moveTo(0, 0);
        int[] secondPointerCoordinates = getPointerCoordinates(mSecondPointLength);
        path.lineTo(secondPointerCoordinates[0], secondPointerCoordinates[1]);
        path.lineTo(secondPointerCoordinates[2], secondPointerCoordinates[3]);
        path.lineTo(secondPointerCoordinates[4], secondPointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mSecond * 6);
        mCanvas.drawPath(path, mPointPaint);
        mCanvas.restore();

        //       这里比较难的可能就是指针的绘制，因为我们的指针是个规则形状，其中getPointerCoordinates便是得到这个不规则形状的3个定点坐标，有兴趣的同学可以去研究一下我的逻辑，也可以定义你自己的逻辑。我的逻辑如下（三角函数学的号的同学应该一眼就能看懂）：

    }

    /**
     * 获取指针坐标
     *
     * @param pointLength 指针长度
     * @return int[]{x1,y1,x2,y2,x3,y3}
     */
    private int[] getPointerCoordinates(int pointLength) {
        int y = (int) (pointLength * 3.0f / 4);
        int x = (int) (y * Math.tan(Math.PI / 180 * 5));
        return new int[]{-x, y, 0, pointLength, x, y};
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        flag = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        flag = false;
    }

    @Override
    public void run() {
        long start, end;
        while (flag) {
            start = System.currentTimeMillis();
            draw();
          //  logic();
            end = System.currentTimeMillis();

            try {
                if (end - start < 1000) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 逻辑
     */
    private void logic() {
        mSecond++;
        if (mSecond == 60) {
            mSecond = 0;
            mMinute++;
            if (mMinute == 60) {
                mMinute = 0;
                mHour++;
                if (mHour == 24) {
                    mHour = 0;
                }
            }
        }
    }
}
