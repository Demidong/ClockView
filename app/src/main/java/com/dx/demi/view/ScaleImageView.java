package com.dx.demi.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by 13798 on 2016/6/3. 
 */
@SuppressLint("AppCompatCustomView")
public class ScaleImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    /** 
     * 控件宽度 
     */  
    private int mWidth;  
    /** 
     * 控件高度 
     */  
    private int mHeight;  
    /** 
     * 拿到src的图片 
     */  
    private Drawable mDrawable;
    /** 
     * 图片宽度（使用前判断mDrawable是否null） 
     */  
    private int mDrawableWidth;  
    /** 
     * 图片高度（使用前判断mDrawable是否null） 
     */  
    private int mDrawableHeight;  
  
    /** 
     * 初始化缩放值 
     */  
    private float mScale;  
  
    /** 
     * 双击图片的缩放值 
     */  
    private float mDoubleClickScale;  
  
    /** 
     * 最大的缩放值 
     */  
    private float mMaxScale;  
  
    /** 
     * 最小的缩放值 
     */  
    private float mMinScale;  
  
    private ScaleGestureDetector scaleGestureDetector;
    /** 
     * 当前有着缩放值、平移值的矩阵。 
     */  
    private Matrix matrix;
  
    public ScaleImageView(Context context) {
        this(context, null);  
    }  
  
    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);  
    }  
  
    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);  
        setOnTouchListener(this);  
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        initListener();  
    }  
  
  
    /** 
     * 初始化事件监听 
     */  
    private void initListener() {  
        // 强制设置模式  
        setScaleType(ScaleType.MATRIX);
        // 添加观察者  
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {  
                // 移除观察者  
                getViewTreeObserver().removeGlobalOnLayoutListener(this);  
                // 获取控件大小  
                mWidth = getWidth();  
                mHeight = getHeight();  
  
                //通过getDrawable获得Src的图片  
                mDrawable = getDrawable();  
                if (mDrawable == null)  
                    return;  
                mDrawableWidth = mDrawable.getIntrinsicWidth();  
                mDrawableHeight = mDrawable.getIntrinsicHeight();  
                initImageViewSize();  
                moveToCenter();  
            }  
        });  
    }  
  
    /** 
     * 初始化资源图片宽高 
     */  
    private void initImageViewSize() {  
        if (mDrawable == null)  
            return;  
  
        // 缩放值  
        float scale = 1.0f;  
        // 图片宽度大于控件宽度，图片高度小于控件高度  
        if (mDrawableWidth > mWidth && mDrawableHeight < mHeight)  
            scale = mWidth * 1.0f / mDrawableWidth;  
            // 图片高度度大于控件宽高，图片宽度小于控件宽度  
        else if (mDrawableHeight > mHeight && mDrawableWidth < mWidth)  
            scale = mHeight * 1.0f / mDrawableHeight;  
            // 图片宽度大于控件宽度，图片高度大于控件高度  
        else if (mDrawableHeight > mHeight && mDrawableWidth > mWidth)  
            scale = Math.min(mHeight * 1.0f / mDrawableHeight, mWidth * 1.0f / mDrawableWidth);
            // 图片宽度小于控件宽度，图片高度小于控件高度  
        else if (mDrawableHeight < mHeight && mDrawableWidth < mWidth)  
            scale = Math.min(mHeight * 1.0f / mDrawableHeight, mWidth * 1.0f / mDrawableWidth);
        mScale = scale;  
        mMaxScale = mScale * 8.0f;  
        mMinScale = mScale * 0.5f;  
    }  
  
    /** 
     * 移动控件中间位置 
     */  
    private void moveToCenter() {  
        final float dx = mWidth / 2 - mDrawableWidth / 2;  
        final float dy = mHeight / 2 - mDrawableHeight / 2;  
        matrix = new Matrix();
        // 平移至中心  
        matrix.postTranslate(dx, dy);  
        // 以控件中心作为缩放  
        matrix.postScale(mScale, mScale, mWidth / 2, mHeight / 2);  
        setImageMatrix(matrix);  
    }  
  
    /** 
     * @return 当前缩放的值 
     */  
    private float getmScale() {  
        float[] floats = new float[9];  
        matrix.getValues(floats);  
        return floats[Matrix.MSCALE_X];
    }  
  
    /** 
     * @param matrix 矩阵 
     * @return matrix的 l t b r 和width，height 
     */  
    private RectF getRectf(Matrix matrix) {
        RectF f = new RectF();
        if (mDrawable == null)  
            return null;  
        f.set(0, 0, mDrawableWidth, mDrawableHeight);  
        matrix.mapRect(f);  
        return f;  
    }  
  
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mDrawable == null) {  
            return true;  
        }  
        // 系统定义的缩放值  
        float scaleFactor = detector.getScaleFactor();  
        // 获取已经缩放的值  
        float scale = getmScale();  
        float scaleResult = scale * scaleFactor;  
        if (scaleResult >= mMaxScale && scaleFactor > 1.0f)  
            scaleFactor = mMaxScale / scale;  
        if (scaleResult <= mMinScale && scaleFactor < 1.0f)  
            scaleFactor = mMinScale / scale;  
  
        matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());  
  
        RectF f = getRectf(matrix);
        float dX = 0.0f;  
        float dY = 0.0f;  
        // 图片高度大于控件高度  
        if (f.height() >= mHeight) {  
            // 图片顶部出现空白  
            if (f.top > 0) {  
                // 往上移动  
                dY = -f.top;  
            }  
            // 图片底部出现空白  
            if (f.bottom < mHeight) {  
                // 往下移动  
                dY = mHeight - f.bottom;  
            }  
        }  
        // 图片宽度大于控件宽度  
        if (f.width() >= mWidth) {  
            // 图片左边出现空白  
            if (f.left > 0) {  
                // 往左边移动  
                dX = -f.left;  
            }  
            // 图片右边出现空白  
            if (f.right < mWidth) {  
                // 往右边移动  
                dX = mWidth - f.right;  
            }  
        }  
  
        if (f.width() < mWidth) {  
            dX = mWidth / 2 - f.right + f.width() / 2;  
        }  
  
        if (f.height() < mHeight) {  
            dY = mHeight / 2 - f.bottom + f.height() / 2;  
        }  
        matrix.postTranslate(dX, dY);  
        setImageMatrix(matrix);  
        return true;  
    }  
  
  
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;  
    }  
  
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        float scale = getmScale();  
        if (scale < mScale) {  
            matrix.postScale(mScale / scale, mScale / scale, mWidth / 2, mHeight / 2);  
            setImageMatrix(matrix);  
        }  
    }  
  
  
    private float downX;  
    private float downY;  
    private float nowMovingX;  
    private float nowMovingY;  
    private float lastMovedX;  
    private float lastMovedY;  
    private boolean isFirstMoved = false;  
  
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isFirstMoved = false;  
                downX = event.getX();  
                downY = event.getY();  
                break;  
            case MotionEvent.ACTION_POINTER_DOWN:
                isFirstMoved = false;  
                break;  
            case MotionEvent.ACTION_MOVE:
                nowMovingX = event.getX();  
                nowMovingY = event.getY();  
                if (!isFirstMoved) {  
                    isFirstMoved = true;  
                    lastMovedX = nowMovingX;  
                    lastMovedY = nowMovingY;  
                }  
                float dX = 0.0f;  
                float dY = 0.0f;  
                RectF rectf = getRectf(matrix);
                // 判断滑动方向  
                final float scrollX = nowMovingX - lastMovedX;  
                // 判断滑动方向  
                final float scrollY = nowMovingY - lastMovedY;  
                // 图片高度大于控件高度  
                if (rectf.height() > mHeight && canSmoothY()) {  
                    dY = nowMovingY - lastMovedY;  
                }  
  
                // 图片宽度大于控件宽度  
                if (rectf.width() > mWidth && canSmoothX()) {  
                    dX = nowMovingX - lastMovedX;  
                }  
                matrix.postTranslate(dX, dY);  
  
                remedyXAndY(dX,dY);  
  
                lastMovedX = nowMovingX;  
                lastMovedY = nowMovingY;  
                break;  
            case MotionEvent.ACTION_UP:
                break;  
            case MotionEvent.ACTION_POINTER_UP:
                isFirstMoved = false;  
                break;  
        }  
        return scaleGestureDetector.onTouchEvent(event);  
    }  
  
    /** 
     * 判断x方向上能不能滑动 
     * @return 可以滑动返回true 
     */  
    private boolean canSmoothX(){  
        RectF rectf = getRectf(matrix);
        if (rectf.left >0 || rectf.right <getWidth())  
            return false;  
        return true;  
    }  
  
    /** 
     * 判断y方向上可不可以滑动 
     * @return 可以滑动返回true 
     */  
    private boolean canSmoothY(){  
        RectF rectf = getRectf(matrix);
        if (rectf.top>0 || rectf.bottom < getHeight())  
            return false;  
        return true;  
    }  
  
    /** 
     * 纠正出界的横和众线 
     * @param dx 出界偏移的横线 
     * @param dy 出街便宜的众线 
     */  
    private void remedyXAndY(float dx,float dy){  
        if (!canSmoothX())  
            matrix.postTranslate(-dx,0);  
        if (!canSmoothY())  
            matrix.postTranslate(0,-dy);  
        setImageMatrix(matrix);  
    }  
}  