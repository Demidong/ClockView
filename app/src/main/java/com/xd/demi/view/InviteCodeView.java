package com.xd.demi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.xd.demi.R;

/**
 * Created by demi on 2019/3/1 下午4:16.
 */
public class InviteCodeView extends AppCompatEditText {
    /**
     * 方框之间的间隙
     */
    private float mGapWidth;

    /**
     * 方框个数
     */
    private int mSquareNum;
    /**
     * 方框大小
     */
    private float mSquareWidth;
    /**
     * 方框内数字大小
     */
    private float mNumberTextSize;

    /**
     * 画数字还是画密码
     */
    private boolean isDrawNumber;
    /**
     * 输入完毕监听
     */
    private OnFinishInputCodeListener finishListener;

    private Paint mPaint;
    private RectF rectF;

    public InviteCodeView(Context context) {
        this(context, null);
    }

    //继承了EditTextView,  super(context, attrs);传入attrs ondDraw()中的代码就画不出来。 super(context, null);不传attrs，onDraw()中的代码可以正常画出来，但使用时，findViewByid找不到id
    public InviteCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InviteCodeView);
        mGapWidth = a.getDimension(R.styleable.InviteCodeView_gapWidth, 60);
        mSquareWidth = a.getDimension(R.styleable.InviteCodeView_squareWidth, 60);
        mSquareNum = a.getInteger(R.styleable.InviteCodeView_squareNum, 6);
        mNumberTextSize = a.getDimension(R.styleable.InviteCodeView_numberTextSize, 40);
        isDrawNumber = a.getBoolean(R.styleable.InviteCodeView_isDrawNumber, true);
        a.recycle();
        setFocusable(true);
        setCursorVisible(false);
        setFocusableInTouchMode(true);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        setBackground(null);
        setRawInputType(InputType.TYPE_CLASS_NUMBER);
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float xOffset = (getWidth() - (mSquareNum * (mSquareWidth + mGapWidth))) / 2;
        canvas.translate(xOffset, getHeight() / 2);
        drawRect(canvas);
        if (isDrawNumber) {
            drawNumberText(canvas, getText().toString());
        } else {
            drawPswText(canvas);
        }
    }

    private void drawNumberText(Canvas canvas, String text) {
        if (TextUtils.isDigitsOnly(text)) {
            mPaint.setColor(Color.GREEN);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(mNumberTextSize);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setTextAlign(Paint.Align.CENTER);
            String[] nums = text.split("");
            rectF.set(0, 0, mSquareWidth, mSquareWidth);
            for (int i = 1; i < nums.length; i++) {
                canvas.drawText(nums[i], rectF.left + mSquareWidth / 2, rectF.bottom - (mSquareWidth - mNumberTextSize) / 2, mPaint);
                rectF.offset(mGapWidth + mSquareWidth, 0);
            }

        }
    }

    private void drawPswText(Canvas canvas) {
        if (TextUtils.isDigitsOnly(getText())) {
            mPaint.setColor(Color.GREEN);
            mPaint.setStrokeWidth(4);
            mPaint.setTextSize(mNumberTextSize);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setTextAlign(Paint.Align.CENTER);
            String[] nums = new String[getText().length()];
            rectF.set(0, 0, mSquareWidth, mSquareWidth);
            for (int i = 0; i < nums.length; i++) {
                nums[i] = "*";
                canvas.drawText(nums[i], rectF.left + mSquareWidth / 2, rectF.bottom - (mSquareWidth - mNumberTextSize) / 2, mPaint);
                rectF.offset(mGapWidth + mSquareWidth, 0);
            }
        }
    }

    private void drawRect(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        rectF.set(0, 0, mSquareWidth, mSquareWidth);
        for (int i = 0; i < mSquareNum; i++) {
            canvas.drawRect(rectF, mPaint);
            rectF.offset(mGapWidth + mSquareWidth, 0);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (text == null) return;
        if (!TextUtils.isDigitsOnly(text)) {
            text = "";
            setText("");
        }
        if (text.length() >= mSquareNum) {
            if (finishListener != null) {
                finishListener.finishInputCode(text.toString());
            }
        }
    }

    public String getNumberText() {
        return getText().toString();
    }

    public void showNumberText() {
        isDrawNumber = true;
        invalidate();
    }

    public void hideNumberText() {
        isDrawNumber = false;
        invalidate();
    }

    public boolean isDrawNumber() {
        return isDrawNumber;
    }

    public interface OnFinishInputCodeListener {
        void finishInputCode(String numberText);
    }

    public void setOnFinishInputCodeListener(OnFinishInputCodeListener finishListener) {
        this.finishListener = finishListener;
    }
}
