package com.dx.demi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.dx.demi.R;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {

    /**
     * 扩散圆圈颜色
     */
    private int mColor = getResources().getColor(R.color.yellow);
    /**
     * 第一个圆圈的半径(也就是圆形图片的半径)
     */
    private int mImageRadius=50;
    /**
     * 扩散圆之间间距
     */
    private int mWidth = 3;
    /**
     * 最大宽度
     */
    private Integer mMaxRadius = 255;
    /**
     * 是否正在扩散中
     */
    private boolean mIsDiffuse = false;
    // 透明度集合
    private List<Integer> mAlphas = new ArrayList<>();
    // 扩散圆半径集合
    private List<Integer> mRadius = new ArrayList<>();
    private Paint mPaint;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiffuseView, defStyleAttr, 0);
        mColor = a.getColor(R.styleable.DiffuseView_diffuse_color, mColor);
        mWidth = a.getInt(R.styleable.DiffuseView_diffuse_width, mWidth);
        mImageRadius = a.getInt(R.styleable.DiffuseView_diffuse_coreImageRadius, mImageRadius);
        a.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mAlphas.add(255);
        mRadius.add(0);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mMaxRadius = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;
    }

    @Override
    public void invalidate() {
        if (hasWindowFocus()) {
            super.invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 绘制扩散圆
        mPaint.setColor(mColor);
        for (int i = 0; i < mAlphas.size(); i++) {
            // 设置透明度
            Integer alpha = mAlphas.get(i);
            mPaint.setAlpha(alpha);
            // 绘制扩散圆
            Integer radius = mRadius.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2,  mImageRadius+radius, mPaint);

            if (alpha > 0 && mImageRadius+radius < mMaxRadius) {
                alpha = (int) (255.0F * (1.0F - (mImageRadius+radius) * 1.0f / mMaxRadius));
                mAlphas.set(i, alpha);
                mRadius.set(i, radius + 1);
            }
        }
        // 判断当扩散圆扩散到指定宽度时添加新扩散圆
        if (mRadius.get(mRadius.size() - 1) == mWidth) {
            mAlphas.add(255);
            mRadius.add(0);
        }
        // 超过10个扩散圆，删除最外层
        if (mRadius.size() >= ((mMaxRadius-mImageRadius)/mWidth)+2) {
            mRadius.remove(0);
            mAlphas.remove(0);
        }

        if (mIsDiffuse) {
            invalidate();
        }
    }

    /**
     * 开始扩散
     */
    public void start() {
        mIsDiffuse = true;
        invalidate();
    }

    /**
     * 停止扩散
     */
    public void stop() {
        mIsDiffuse = false;
    }

    /**
     * 是否扩散中
     */
    public boolean isDiffuse() {
        return mIsDiffuse;
    }

    /**
     * 设置扩散圆颜色
     */
    public void setColor(int colorId) {
        mColor = colorId;
    }

    /**
     * 设置扩散圆宽度(值越小宽度越大)
     */
    public void setmWidth(int width) {
        mWidth = width;
    }

    /**
     * 设置最大宽度
     */
    public void setmMaxRadius(int maxRadius) {
        mMaxRadius = maxRadius;
    }
}