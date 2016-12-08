package com.dx.demi.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by demi on 16/12/6.
 */

public class EventView extends View {
    private Paint mPaint = new Paint();
    private float x = 0, y = 0;
    private TextView textView;

    public EventView(Context context) {
        this(context, null);
    }

    public EventView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("View onTouchEvent" + event.getAction());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                x = 0;
                y = 0;
                invalidate();
                break;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("View dispatchTouchEvent" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(x, 0, x, y, mPaint);
        canvas.drawLine(0, y, x, y, mPaint);
        if (textView != null) {
            textView.setText(getTextString());
        }
    }

    private String getTextString() {
        return "坐标 ( " + x + "," + y + " )";
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

}
