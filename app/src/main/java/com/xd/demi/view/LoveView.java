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
import com.xd.demi.activity.LoveActivity;

import java.util.ArrayList;

/**
 * Created by demi on 2018/12/6 下午5:39.
 */
public class LoveView extends View {
    Paint mPaint = null;
    float x = 0, y = 0;
    float handX = 0, handY = 0;
    int alphaValue;
    Path path = new Path();
    Point mPoint = new Point(0,0);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <mLoveHearts.size() ; i++) {
            drawFlower(canvas,mLoveHearts.get(i));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flower_a);
                LoveHeart loveHeart = new LoveHeart(bitmap,255,event.getX()-bitmap.getWidth()/2,event.getY()-bitmap.getHeight()/2);
                createValueAnimator(loveHeart).start();
                mLoveHearts.add(loveHeart);
                break;

        }
        return true;
    }

    public ValueAnimator createValueAnimator(final LoveHeart loveHeart){
        ValueAnimator animator =  ValueAnimator.ofFloat(0,255).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loveHeart.alpha  = (int) (255-(float)animation.getAnimatedValue());
                loveHeart.offset  = (float) animation.getAnimatedValue() * 2;
                invalidate();
                if(animation.getAnimatedFraction() ==1){
                    mLoveHearts.remove(loveHeart);
                }
            }
        });
       return  animator;
    }

    public void drawFlower(Canvas canvas, LoveHeart loveHeart){
        mPaint.setAlpha(loveHeart.alpha);
        canvas.drawBitmap(loveHeart.bitmap,loveHeart.x,loveHeart.y-loveHeart.offset,mPaint);
    }
    class LoveHeart {
        Bitmap bitmap;
        int alpha;
        float x ;
        float y ;
        float offset ;

        public LoveHeart(Bitmap bitmap, int alpha, float x, float y) {
            this.bitmap = bitmap;
            this.alpha = alpha;
            this.x = x;
            this.y = y;
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
    }
}
