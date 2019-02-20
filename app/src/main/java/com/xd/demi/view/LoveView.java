package com.xd.demi.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.xd.demi.R;

import java.util.ArrayList;

/**
 * Created by demi on 2018/12/6 下午5:39.
 */
public class LoveView extends View {
    Paint mPaint = null;
    float x = 0, y = 0;
    float lastX = 0, lastY = 0;
    int alphaValue;
    Path path = new Path();
    Point mPoint = new Point(0, 0);
    ArrayList<LoveHeart> mLoveHearts = new ArrayList<>();

    public LoveView(Context context) {
        this(context, null);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
//        for (int i = 0; i <= width; i += width/5) {
//            for (int j = 0; j <= height; j += height/5) {
//                if (i == 0 || i == width  || j == 0 || j == height ) {
//                    LoveHeart loveHeart = new LoveHeart(10, 255, Color.RED,i, j);
//                    mLoveHearts.add(loveHeart);
//                }
//            }
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLoveHearts.size(); i++) {
            drawFlower(canvas, mLoveHearts.get(i));
        }
        mPaint.setStyle(Paint.Style.FILL);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_heart);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
               int num = (int) (Math.random() * 10000);
               String a = String.valueOf(num);
                if(a.length() != 4){
                    a="0000";
                }
                LoveHeart loveHeart = new LoveHeart(5, 255,Color.parseColor("#ff"+a), event.getX(), event.getY());
                createValueAnimator(loveHeart).start();
                mLoveHearts.add(loveHeart);
                invalidate();
                lastX = event.getX();
                lastY = event.getY();
                break;

        }
        return true;
    }

    public ValueAnimator createValueAnimator(final LoveHeart loveHeart) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 255).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loveHeart.alpha = 255 - (int) animation.getAnimatedValue();
                loveHeart.offset = (int) animation.getAnimatedValue() * 2;
                loveHeart.size = (255 - (int) animation.getAnimatedValue()) / 30;
                invalidate();
                if (animation.getAnimatedFraction() == 1) {
                    mLoveHearts.remove(loveHeart);
                }
            }
        });
        return animator;
    }

    public Point getHeartPoint(float angle, int offsetX, int offsetY, int size) {
        float t = (float) (angle / Math.PI);
        float x = (float) (size * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-size * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    public void drawFlower(Canvas canvas, LoveHeart loveHeart) {
        mPaint.setAlpha(loveHeart.alpha);
        Path path = new Path();
        for (float i = 0; i <= 180; i += 0.25) {
            Point point = getHeartPoint(i, (int) loveHeart.x, (int) (loveHeart.y - loveHeart.offset), loveHeart.size);
            if (point.x == 0 || point.y == 0)
                continue;
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
//            canvas.drawPoint(point.x, point.y, mPaint);
//            canvas.drawBitmap(bitmap, point.x - bitmap.getWidth() / 2, point.y - bitmap.getHeight() / 2, mPaint);
        }
        mPaint.setColor(loveHeart.color);
        canvas.drawPath(path, mPaint);
//        canvas.drawBitmap(loveHeart.bitmap, loveHeart.x, loveHeart.y - loveHeart.offset, mPaint);
    }

    class LoveHeart {
        Bitmap bitmap;
        int alpha;
        float x;
        float y;
        int size;
        float offset;
        int color;

        public LoveHeart(Bitmap bitmap, int alpha, float x, float y) {
            this.bitmap = bitmap;
            this.alpha = alpha;
            this.x = x;
            this.y = y;
        }

        public LoveHeart(int size, int alpha,int color, float x, float y) {
            this.alpha = alpha;
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getOffset() {
            return offset;
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
