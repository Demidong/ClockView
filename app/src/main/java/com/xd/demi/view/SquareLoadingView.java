package com.xd.demi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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
    /**
     * 固定方块 & 移动方块变量
     */
    private FixedBlock[] mFixedBlocks;
    private MoveBlock mMoveBlock;

    private Paint mPaint;
    // 动画属性
    private float mRotateDegree;
    private boolean mAllowRoll = false;
    private boolean isMoving = false;

    private AnimatorSet mAnimatorSet;

    public SquareLoadingView(Context context) {
        this(context, null);
    }

    public SquareLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttris(context, attrs);
        init();
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
        if (isInsideTheRect(initPosition, lineNumber)) {
            initPosition = 0;
        }
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
        mPaint = new Paint();
        mPaint.setColor(blockColor);
        mPaint.setStyle(Paint.Style.FILL);
        initBlocks();
    }

    private boolean isInsideTheRect(int pos, int lineCount) {
        // 判断方块是否在第1行
        if (pos < lineCount) {
            return false;
            // 是否在最后1行
        } else if (pos > (lineCount * lineCount - 1 - lineCount)) {
            return false;
            // 是否在最后1行
        } else if ((pos + 1) % lineCount == 0) {
            return false;
            // 是否在第1行
        } else if (pos % lineCount == 0) {
            return false;
        }
        // 若不在4边，则在内部
        return true;
    }

    private void initBlocks() {
        mFixedBlocks = new FixedBlock[lineNumber * lineNumber];
        for (int i = 0; i < mFixedBlocks.length; i++) {
            mFixedBlocks[i] = new FixedBlock();
            mFixedBlocks[i].index = i;
            mFixedBlocks[i].isShow = initPosition != i;
            mFixedBlocks[i].rectF = new RectF();
        }
        mMoveBlock = new MoveBlock();
        mMoveBlock.rectF = new RectF();
        relate_OuterBlock(mFixedBlocks, isClockWise);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        // 1. 设置移动方块的旋转中心坐标
        int cx = measuredWidth / 2;
        int cy = measuredHeight / 2;

        // 2. 设置固定方块的位置 ->>关注1
        fixedBlockPosition(mFixedBlocks, cx, cy, blockInterval, half_BlockWidth);
        // 3. 设置移动方块的位置 ->>关注2
        MoveBlockPosition(mFixedBlocks, mMoveBlock, initPosition, isClockWise);
    }

    /**
     * 设置 固定方块位置
     */
    private void fixedBlockPosition(FixedBlock[] fixedBlocks, int cx, int cy, float dividerWidth, float halfSquareWidth) {

        // 1. 确定第1个方块的位置
        // 分为2种情况：行数 = 偶 / 奇数时
        // 主要是是数学知识，此处不作过多描述
        float squareWidth = halfSquareWidth * 2;
        int lineCount = (int) Math.sqrt(fixedBlocks.length);
        float firstRectLeft = 0;
        float firstRectTop = 0;

        // 情况1：当行数 = 偶数时
        if (lineCount % 2 == 0) {
            int squareCountInAline = lineCount / 2;
            int diviCountInAline = squareCountInAline - 1;
            float firstRectLeftTopFromCenter = squareCountInAline * squareWidth
                    + diviCountInAline * dividerWidth
                    + dividerWidth / 2;
            firstRectLeft = cx - firstRectLeftTopFromCenter;
            firstRectTop = cy - firstRectLeftTopFromCenter;

            // 情况2：当行数 = 奇数时
        } else {
            int squareCountInAline = lineCount / 2;
            int diviCountInAline = squareCountInAline;
            float firstRectLeftTopFromCenter = squareCountInAline * squareWidth
                    + diviCountInAline * dividerWidth
                    + halfSquareWidth;
            firstRectLeft = cx - firstRectLeftTopFromCenter;
            firstRectTop = cy - firstRectLeftTopFromCenter;
        }

        // 2. 确定剩下的方块位置
        // 思想：把第一行方块位置往下移动即可
        // 通过for循环确定：第一个for循环 = 行，第二个 = 列
        for (int i = 0; i < lineCount; i++) {//行
            for (int j = 0; j < lineCount; j++) {//列
                if (i == 0) {
                    if (j == 0) {
                        fixedBlocks[0].rectF.set(firstRectLeft, firstRectTop,
                                firstRectLeft + squareWidth, firstRectTop + squareWidth);
                    } else {
                        int currIndex = i * lineCount + j;
                        fixedBlocks[currIndex].rectF.set(fixedBlocks[currIndex - 1].rectF);
                        fixedBlocks[currIndex].rectF.offset(dividerWidth + squareWidth, 0);
                    }
                } else {
                    int currIndex = i * lineCount + j;
                    fixedBlocks[currIndex].rectF.set(fixedBlocks[currIndex - lineCount].rectF);
                    fixedBlocks[currIndex].rectF.offset(0, dividerWidth + squareWidth);
                }
            }
        }
    }

    /**
     * 设置移动方块的位置
     */
    private void MoveBlockPosition(FixedBlock[] fixedBlocks,
                                   MoveBlock moveBlock, int initPosition, boolean isClockwise) {

        // 移动方块位置 = 设置初始的空出位置 的下一个位置（next）
        // 下一个位置 通过 连接的外部方块位置确定
        FixedBlock fixedBlock = fixedBlocks[initPosition];
        moveBlock.rectF.set(fixedBlock.next.rectF);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFixedBlock(canvas);
        drawMoveBlock(canvas);
    }

    private void drawMoveBlock(Canvas canvas) {
        if (mMoveBlock.isShow) {
            canvas.rotate(isClockWise ? mRotateDegree : -mRotateDegree, mMoveBlock.cx, mMoveBlock.cy);
            canvas.drawRoundRect(mMoveBlock.rectF, moveBlock_Angle, moveBlock_Angle, mPaint);
        }
    }

    private void drawFixedBlock(Canvas canvas) {
        for (FixedBlock mfixedBlock : mFixedBlocks) {
            if (mfixedBlock.isShow) {
                canvas.drawRoundRect(mfixedBlock.rectF, fixBlock_Angle, fixBlock_Angle, mPaint);
            }
        }
    }

    /**
     * 关注4：将外部方块的位置关联起来
     * 算法思想： 按照第1行、最后1行、第1列 & 最后1列的顺序，分别让每个外部方块的next属性 == 下一个外部方块的位置，最终对整个外部方块的位置进行关联
     * 注：需要考虑移动方向变量isClockwise（ 顺 Or 逆时针）
     */

    private void relate_OuterBlock(FixedBlock[] fixedBlocks, boolean isClockwise) {
        int lineCount = (int) Math.sqrt(fixedBlocks.length);

        // 情况1：关联第1行
        for (int i = 0; i < lineCount; i++) {
            // 位于最左边
            if (i % lineCount == 0) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i + lineCount] : fixedBlocks[i + 1];
                // 位于最右边
            } else if ((i + 1) % lineCount == 0) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i - 1] : fixedBlocks[i + lineCount];
                // 中间
            } else {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i - 1] : fixedBlocks[i + 1];
            }
        }
        // 情况2：关联最后1行
        for (int i = (lineCount - 1) * lineCount; i < lineCount * lineCount; i++) {
            // 位于最左边
            if (i % lineCount == 0) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i + 1] : fixedBlocks[i - lineCount];
                // 位于最右边
            } else if ((i + 1) % lineCount == 0) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i - lineCount] : fixedBlocks[i - 1];
                // 中间
            } else {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i + 1] : fixedBlocks[i - 1];
            }
        }

        // 情况3：关联第1列
        for (int i = 1 * lineCount; i <= (lineCount - 1) * lineCount; i += lineCount) {
            // 若是第1列最后1个
            if (i == (lineCount - 1) * lineCount) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i + 1] : fixedBlocks[i - lineCount];
                continue;
            }
            fixedBlocks[i].next = isClockwise ? fixedBlocks[i + lineCount] : fixedBlocks[i - lineCount];
        }

        // 情况4：关联最后1列
        for (int i = 2 * lineCount - 1; i <= lineCount * lineCount - 1; i += lineCount) {
            // 若是最后1列最后1个
            if (i == lineCount * lineCount - 1) {
                fixedBlocks[i].next = isClockwise ? fixedBlocks[i - lineCount] : fixedBlocks[i - 1];
                continue;
            }
            fixedBlocks[i].next = isClockwise ? fixedBlocks[i - lineCount] : fixedBlocks[i + lineCount];
        }
    }

    /**
     * 步骤5：启动动画
     */

    public void startMoving() {

        // 1. 根据标志位 & 视图是否可见确定是否需要启动动画
        // 此处设置是为了方便手动 & 自动停止动画
        if (isMoving || getVisibility() != View.VISIBLE) {
            return;
        }

        // 设置标记位：以便是否停止动画
        isMoving = true;
        mAllowRoll = true;

        // 2. 获取固定方块当前的空位置，即移动方块当前位置
        FixedBlock currEmptyfixedBlock = mFixedBlocks[mCurrEmptyPosition];
        // 3. 获取移动方块的到达位置，即固定方块当前空位置的下1个位置
        FixedBlock movedBlock = currEmptyfixedBlock.next;

        // 4. 设置方块动画 = 移动方块平移 + 旋转
        // 原理：设置平移动画（Translate） + 旋转动画（Rotate），最终通过组合动画（AnimatorSet）组合起来

        // 4.1 设置平移动画：createTranslateValueAnimator（） ->>关注1
        mAnimatorSet = new AnimatorSet();
        // 平移路径 = 初始位置 - 到达位置
        ValueAnimator translateConrtroller = createTranslateValueAnimator(currEmptyfixedBlock,
                movedBlock);

        // 4.2 设置旋转动画：createMoveValueAnimator(（）->>关注3
        ValueAnimator moveConrtroller = createMoveValueAnimator();

        // 4.3 将两个动画组合起来
        // 设置移动的插值器
        mAnimatorSet.setInterpolator(moveInterpolator);
        mAnimatorSet.playTogether(translateConrtroller, moveConrtroller);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {

            // 动画开始时进行一些设置
            @Override
            public void onAnimationStart(Animator animation) {

                // 每次动画开始前都需要更新移动方块的位置 ->>关注4
                updateMoveBlock();

                // 让移动方块的初始位置的下个位置也隐藏 = 两个隐藏的方块
                mFixedBlocks[mCurrEmptyPosition].next.isShow = false;

                // 通过标志位将移动的方块显示出来
                mMoveBlock.isShow = true;
            }

            // 结束时进行一些设置
            @Override
            public void onAnimationEnd(Animator animation) {
                isMoving = false;
                mFixedBlocks[mCurrEmptyPosition].isShow = true;
                mCurrEmptyPosition = mFixedBlocks[mCurrEmptyPosition].next.index;

                // 将移动的方块隐藏
                mMoveBlock.isShow = false;

                // 通过标志位判断动画是否要循环播放
                if (mAllowRoll) {
                    startMoving();
                }
            }
        });

        // 启动动画
        mAnimatorSet.start();
    }

    /**
     * 关注1：设置平移动画
     */
    private ValueAnimator createTranslateValueAnimator(FixedBlock currEmptyfixedBlock,
                                                       FixedBlock moveBlock) {
        float startAnimValue = 0;
        float endAnimValue = 0;
        PropertyValuesHolder left = null;
        PropertyValuesHolder top = null;

        // 1. 设置移动速度
        ValueAnimator valueAnimator = new ValueAnimator().setDuration(moveSpeed);


        // 2. 设置移动方向
        // 情况分为：4种，分别是移动方块向左、右移动 和 上、下移动
        // 注：需考虑 旋转方向（isClock_Wise），即顺逆时针 ->>关注1.1
        if (isNextRollLeftOrRight(currEmptyfixedBlock, moveBlock)) {

            // 情况1：顺时针且在第一行 / 逆时针且在最后一行时，移动方块向右移动
            if (isClockWise && currEmptyfixedBlock.index > moveBlock.index || !isClockWise && currEmptyfixedBlock.index > moveBlock.index) {

                startAnimValue = moveBlock.rectF.left;
                endAnimValue = moveBlock.rectF.left + blockInterval;

                // 情况2：顺时针且在最后一行 / 逆时针且在第一行，移动方块向左移动
            } else if (isClockWise && currEmptyfixedBlock.index < moveBlock.index
                    || !isClockWise && currEmptyfixedBlock.index < moveBlock.index) {

                startAnimValue = moveBlock.rectF.left;
                endAnimValue = moveBlock.rectF.left - blockInterval;
            }

            // 设置属性值
            left = PropertyValuesHolder.ofFloat("left", startAnimValue, endAnimValue);
            valueAnimator.setValues(left);

        } else {
            // 情况3：顺时针且在最左列 / 逆时针且在最右列，移动方块向上移动
            if (isClockWise && currEmptyfixedBlock.index < moveBlock.index
                    || !isClockWise && currEmptyfixedBlock.index < moveBlock.index) {

                startAnimValue = moveBlock.rectF.top;
                endAnimValue = moveBlock.rectF.top - blockInterval;

                // 情况4：顺时针且在最右列 / 逆时针且在最左列，移动方块向下移动
            } else if (isClockWise && currEmptyfixedBlock.index > moveBlock.index
                    || !isClockWise && currEmptyfixedBlock.index > moveBlock.index) {
                startAnimValue = moveBlock.rectF.top;
                endAnimValue = moveBlock.rectF.top + blockInterval;
            }

            // 设置属性值
            top = PropertyValuesHolder.ofFloat("top", startAnimValue, endAnimValue);
            valueAnimator.setValues(top);
        }

        // 3. 通过监听器更新属性值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object left = animation.getAnimatedValue("left");
                Object top = animation.getAnimatedValue("top");
                if (left != null) {
                    mMoveBlock.rectF.offsetTo((Float) left, mMoveBlock.rectF.top);
                }
                if (top != null) {
                    mMoveBlock.rectF.offsetTo(mMoveBlock.rectF.left, (Float) top);
                }
                // 实时更新旋转中心 ->>关注2
                setMoveBlockRotateCenter(mMoveBlock, isClockWise);

                // 更新绘制
                invalidate();
            }
        });
        return valueAnimator;
    }
    // 回到原处


    /**
     * 关注1.1：判断移动方向
     * 即上下 or 左右
     */
    private boolean isNextRollLeftOrRight(FixedBlock currEmptyfixedBlock, FixedBlock rollSquare) {
        if (currEmptyfixedBlock.rectF.left - rollSquare.rectF.left == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 关注2：实时更新移动方块的旋转中心
     * 因为方块在平移旋转过程中，旋转中心也会跟着改变，因此需要改变MoveBlock的旋转中心（cx,cy）
     */

    private void setMoveBlockRotateCenter(MoveBlock moveBlock, boolean isClockwise) {

        // 情况1：以移动方块的左上角为旋转中心
        if (moveBlock.index == 0) {
            moveBlock.cx = moveBlock.rectF.right;
            moveBlock.cy = moveBlock.rectF.bottom;

            // 情况2：以移动方块的右下角为旋转中心
        } else if (moveBlock.index == lineNumber * lineNumber - 1) {
            moveBlock.cx = moveBlock.rectF.left;
            moveBlock.cy = moveBlock.rectF.top;

            // 情况3：以移动方块的左下角为旋转中心
        } else if (moveBlock.index == lineNumber * (lineNumber - 1)) {
            moveBlock.cx = moveBlock.rectF.right;
            moveBlock.cy = moveBlock.rectF.top;

            // 情况4：以移动方块的右上角为旋转中心
        } else if (moveBlock.index == lineNumber - 1) {
            moveBlock.cx = moveBlock.rectF.left;
            moveBlock.cy = moveBlock.rectF.bottom;
        }

        //以下判断与旋转方向有关：即顺 or 逆顺时针

        // 情况1：左边
        else if (moveBlock.index % lineNumber == 0) {
            moveBlock.cx = moveBlock.rectF.right;
            moveBlock.cy = isClockwise ? moveBlock.rectF.top : moveBlock.rectF.bottom;

            // 情况2：上边
        } else if (moveBlock.index < lineNumber) {
            moveBlock.cx = isClockwise ? moveBlock.rectF.right : moveBlock.rectF.left;
            moveBlock.cy = moveBlock.rectF.bottom;

            // 情况3：右边
        } else if ((moveBlock.index + 1) % lineNumber == 0) {
            moveBlock.cx = moveBlock.rectF.left;
            moveBlock.cy = isClockwise ? moveBlock.rectF.bottom : moveBlock.rectF.top;

            // 情况4：下边
        } else if (moveBlock.index > (lineNumber - 1) * lineNumber) {
            moveBlock.cx = isClockwise ? moveBlock.rectF.left : moveBlock.rectF.right;
            moveBlock.cy = moveBlock.rectF.top;
        }
    }
    // 回到原处


    /**
     * 关注3：设置旋转动画
     */
    private ValueAnimator createMoveValueAnimator() {

        // 通过属性动画进行设置
        ValueAnimator moveAnim = ValueAnimator.ofFloat(0, 90).setDuration(moveSpeed);

        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();

                // 赋值
                mRotateDegree = (float) animatedValue;

                // 更新视图
                invalidate();
            }
        });
        return moveAnim;
    }
    // 回到原处

    /**
     * 关注4：更新移动方块的位置
     */

    private void updateMoveBlock() {

        mMoveBlock.rectF.set(mFixedBlocks[mCurrEmptyPosition].next.rectF);
        mMoveBlock.index = mFixedBlocks[mCurrEmptyPosition].next.index;
        setMoveBlockRotateCenter(mMoveBlock, isClockWise);
    }
    // 回到原处


    /**
     * 停止动画
     */
    public void stopMoving() {

        // 通过标记位来设置
        mAllowRoll = false;
    }

    class MoveBlock {
        // 存储方块的坐标位置参数
        RectF rectF;

        // 方块对应序号
        int index;

        // 标志位：判断是否需要绘制
        boolean isShow;

        // 旋转中心坐标
        // 移动时的旋转中心（X，Y）
        float cx;
        float cy;
    }

    class FixedBlock {
        // 存储方块的坐标位置参数
        RectF rectF;

        // 方块对应序号
        int index;

        // 标志位：判断是否需要绘制
        boolean isShow;

        // 指向下一个需要移动的位置
        FixedBlock next;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimatorSet.removeAllListeners();
        mAnimatorSet.cancel();
    }
}
