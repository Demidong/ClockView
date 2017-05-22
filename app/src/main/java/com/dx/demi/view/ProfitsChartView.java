package com.dx.demi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


import com.dx.demi.R;
import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by demi on 2017/5/16.
 */

public class ProfitsChartView extends View {

    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<ListInfo<Profits>> data = new ArrayList<>();
    private ArrayList<Profits> redPoints  = new ArrayList<>();
    private ArrayList<Profits> bluePoints  = new ArrayList<>();
    private double  highestPoint ;
    private double  lowestPoint ;
    protected Paint chartRedLinePaint;
    protected Paint chartBlueLinePaint;
    protected Path mPath;
    private Paint textPaint;
    protected ViewPortHandler viewPortHandler;
    public ProfitsChartView(Context context) {
        this(context, null);
    }

    public ProfitsChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProfitsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        chartRedLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartRedLinePaint.setStyle(Paint.Style.STROKE);
        chartRedLinePaint.setStrokeWidth(2);
        chartRedLinePaint.setAntiAlias(true);
        chartRedLinePaint.setColor(Color.RED);

        chartBlueLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartBlueLinePaint.setStyle(Paint.Style.STROKE);
        chartBlueLinePaint.setStrokeWidth(2);
        chartBlueLinePaint.setColor(getContext().getResources().getColor(R.color.num_blue));
        chartRedLinePaint.setAntiAlias(true);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(2);
        textPaint.setColor(Color.WHITE);
        mPath = new Path();
        viewPortHandler = new ViewPortHandler();
    }

    public ArrayList<ListInfo<Profits>> getData() {
        return data;
    }

    public void setData(ArrayList<ListInfo<Profits>> data) {
        this.data = data;
        getPoints();
        postInvalidate();
    }

    private void getPoints(){
        for (int i = 0; i < data.size(); i++) {
            ListInfo<Profits> list = data.get(i);
            if (list.getType().equals("PORTFOLIO")) {
                bluePoints.clear();
                bluePoints.addAll(list.getList());
            } else {
                redPoints.clear();
                redPoints.addAll(list.getList());
            }
        }

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w > 0 && h > 0 && w < 10000 && h < 10000) {
            viewPortHandler.setCanvasDimens(w, h);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChartLine(canvas);
    }
    private void drawChartLine(Canvas canvas){
        if (redPoints != null && redPoints.size() != 0||bluePoints != null && bluePoints.size() != 0) {
            drawBlueChartLine(canvas);
            drawRedChartLine(canvas);
        }
    }

    private void drawRedChartLine(Canvas canvas) {
        mPath.moveTo(computePx(0,redPoints.size()), computePy((float) redPoints.get(0).getYield()));
        for (int i = 1; i <redPoints.size() ; i++) {
            Profits point = redPoints.get(i);
            mPath.lineTo(computePx(i,redPoints.size()), computePy((float) point.getYield()));
        }
        canvas.drawPath(mPath,chartRedLinePaint);
        mPath.reset();
    }
    private void drawBlueChartLine(Canvas canvas) {
        mPath.moveTo(computePx(0,bluePoints.size()), computePy((float) bluePoints.get(0).getYield()));
        for (int i = 1; i <bluePoints.size() ; i++) {
            Profits point = bluePoints.get(i);
            mPath.lineTo(computePx(i,bluePoints.size()), computePy((float) point.getYield()));
        }
        canvas.drawPath(mPath,chartBlueLinePaint);
        mPath.reset();
    }

    protected float computePx(int xIndex, int totalPoints ) {

        return (getWidth() / (float) (totalPoints - 1)) * xIndex;
    }

    protected float computePy(float val) {
        return (float) (( highestPoint- val) / (highestPoint - lowestPoint) * viewPortHandler.contentHeight());
    }

    protected int computeXIndex(float px,int totalPoints) {
        int xIndex = (int) (px / (getWidth() / (float) (totalPoints - 1)));
        return xIndex;
    }

    public double getHighestPoint() {
        return highestPoint;
    }

    public void setHighestPoint(double highestPoint) {
        this.highestPoint = highestPoint;
    }

    public double getLowestPoint() {
        return lowestPoint;
    }

    public void setLowestPoint(double lowestPoint) {
        this.lowestPoint = lowestPoint;
    }
}
