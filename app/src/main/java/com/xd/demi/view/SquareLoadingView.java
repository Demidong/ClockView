package com.xd.demi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.xd.demi.R;

/**
 * Created by demi on 2019/2/20 上午11:02.
 */
public class SquareLoadingView extends View {
    /**
     * 行数
     */
    private int lineNumber;
    /**
     * 半个方块的宽度
     */
    private float half_BlockWidth;
    /**
     * 方块之间的间隙
     */
    private float blockInterval;
    /**
     * 移动方块的圆角半径
     */
    private float moveBlock_Angle;
    /**
     * 固定方块的圆角半径
     */
    private float fixBlock_Angle;
    /**
     * 方块颜色
     */
    private int blockColor;
    /**
     * 初始位置，空白位置
     */
    private int initPosition;
    /**
     * 动画旋转方向 == 顺时针
     */
    private boolean isClockWise;
    /**
     * 移动速度
     */
    private int moveSpeed;
    /**
     * 移动方块动画的插值器
     */
    private Interpolator moveInterpolator;
    /**
     * 当前空白位置
     */
    private int mCurrEmptyPosition;

    public SquareLoadingView(Context context) {
        this(context, null);
    }

    public SquareLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttris(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SquareLoadingView);
        // 一行的数量(最少3行)
        lineNumber = ta.getInteger(R.styleable.SquareLoadingView_lineNumber, 3);
        if (lineNumber < 3) {
            lineNumber = 3;
        }

        // 半个方块的宽度（dp）
        half_BlockWidth = ta.getDimension(R.styleable.SquareLoadingView_half_BlockWidth, 30);
        // 方块间隔宽度（dp）
        blockInterval = ta.getDimension(R.styleable.SquareLoadingView_blockInterval, 10);

        // 移动方块的圆角半径
        moveBlock_Angle = ta.getFloat(R.styleable.SquareLoadingView_moveBlock_Angle, 10);
        // 固定方块的圆角半径
        fixBlock_Angle = ta.getFloat(R.styleable.SquareLoadingView_fixBlock_Angle, 30);
        // 通过设置两个方块的圆角半径使得二者不同可以得到更好的动画效果哦

        // 方块颜色（使用十六进制代码，如#333、#8e8e8e）
        int defaultColor = context.getResources().getColor(R.color.colorAccent); // 默认颜色
        blockColor = ta.getColor(R.styleable.SquareLoadingView_blockColor, defaultColor);

        // 移动方块的初始位置（即空白位置）
        initPosition = ta.getInteger(R.styleable.SquareLoadingView_initPosition, 0);

        // 由于移动方块只能是外部方块，所以这里需要判断方块是否属于外部方块 -->关注1
//        if (isInsideTheRect(initPosition, lineNumber)) {
//            initPosition = 0;
//        }
        // 动画方向是否 = 顺时针旋转
        isClockWise = ta.getBoolean(R.styleable.SquareLoadingView_isClock_Wise, true);

        // 移动方块的移动速度
        // 注：不建议使用者将速度调得过快
        // 因为会导致ValueAnimator动画对象频繁重复的创建，存在内存抖动
        moveSpeed = ta.getInteger(R.styleable.SquareLoadingView_moveSpeed, 250);

        // 设置移动方块动画的插值器
        int move_InterpolatorResId = ta.getResourceId(R.styleable.SquareLoadingView_move_Interpolator,
                android.R.anim.linear_interpolator);
        moveInterpolator = AnimationUtils.loadInterpolator(context, move_InterpolatorResId);

        // 当方块移动后，需要实时更新的空白方块的位置
        mCurrEmptyPosition = initPosition;

        // 释放资源
        ta.recycle();
    }

    private void init() {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private void createAnimation() {

    }

    class Square {

    }
}
