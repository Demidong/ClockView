package com.dx.demi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.dx.demi.R;
import com.dx.demi.bean.DailyYeildsInfo;
import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.utils.FormatUtil;
import com.dx.demi.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by demi on 2017/5/16.
 */

public class ProfitsChartView extends View {

    private DailyYeildsInfo data = new DailyYeildsInfo();
    private ArrayList<Profits> redPoints = new ArrayList<>();
    private ArrayList<Profits> bluePoints = new ArrayList<>();
    private double highestPoint; //数据最高点
    private double lowestPoint;  //数据最低点
    private double highestYAxis;  //最高坐标刻度
    private double lowestYAxis;   //最低坐标刻度
    protected Paint chartRedLinePaint;
    protected Paint chartBlueLinePaint;
    protected Path mPath;
    private Paint textPaint;
    private double percentAxisGap;   //刻度间隙
    private int yAxisNum = 0;       //坐标个数-1
    private int topOffSet = 30;      //第一条刻度离View的上边缘的距离
    private float rightOffSet; //右边刻度占的距离 不让曲线画到刻度上去
    private float bottomOffSet; //底部时间刻度文字占的高度
    private float xAxisHeght; //上部分和下部分的offset之和，再加上一定的offset,不至于时间轴和收益轴很挤！

    public ProfitsChartView(Context context) {
        this(context, null);
    }

    public ProfitsChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfitsChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
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
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(2);
        textPaint.setColor(Color.WHITE);
        mPath = new Path();
    }

    public DailyYeildsInfo getData() {
        return data;
    }

    public void setData(DailyYeildsInfo data) {
        this.data = data;
        highestPoint=0.0;
        lowestPoint=0.0;
        getPoints();
        postInvalidate();
    }

    private void getPoints() {
        bluePoints.clear();
        redPoints.clear();
        if (data != null && data.getHS300() != null && data.getPORTFOLIO() != null) {
            bluePoints.addAll(data.getPORTFOLIO());
            redPoints.addAll(data.getHS300());
        }
        calculateDatas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (yAxisNum == 0 || data == null || bluePoints.size() <= 1 || redPoints.size() <= 1) {
            drawEmpty(canvas);     //如果没有数据
        }else{
            canvas.translate(0, topOffSet);
            drawChartXAxis(canvas);     //画时间轴坐标刻度
            drawChartYAxis(canvas);     //画Y轴坐标刻度
            drawChartXAxisLines(canvas); //画与时间轴平行的直线
            drawChart(canvas);     //画数据对应的曲线
        }
    }

    private void drawEmpty(Canvas canvas) {
        String text ="暂无可显示的数据";
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
        getFontHeight(textPaint);
        canvas.drawText(text, getWidth()/2, getHeight()/2+getFontHeight(textPaint)/2, textPaint);
    }


    private void drawChart(Canvas canvas) {
        if (redPoints != null && redPoints.size() != 0 || bluePoints != null && bluePoints.size() != 0) {
            drawBlueChartLine(canvas);
            drawRedChartLine(canvas);
        }
    }

    private void drawChartXAxis(Canvas canvas) {
        String start = FormatUtil.formatData(bluePoints.get(0).getTime(), "yyyy-MM-dd");
        String center;
        if (bluePoints.size() % 2 == 0) {
            center = FormatUtil.formatData(bluePoints.get(bluePoints.size() / 2 -1).getTime(), "yyyy-MM-dd");
        } else {
            center = FormatUtil.formatData(bluePoints.get((bluePoints.size() + 1) / 2 -1).getTime(), "yyyy-MM-dd");
        }
        String end = FormatUtil.formatData(bluePoints.get(bluePoints.size() - 1).getTime(), "yyyy-MM-dd");
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.LEFT);
        bottomOffSet = getFontHeight(textPaint);
        xAxisHeght = topOffSet + bottomOffSet + 40;
        canvas.drawText(start, 0, canvas.getHeight() - 2 * bottomOffSet, textPaint);
        if(bluePoints.size()!=1||bluePoints.size()!=2){
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(center, getWidth() / 2, getHeight() - 2 * bottomOffSet, textPaint);
        }
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(end, getWidth(), getHeight() - 2 * bottomOffSet, textPaint);
    }

    private void drawChartYAxis(Canvas canvas) {
        String shigh = FormatUtil.format(highestYAxis * 100, 2) + "%";
        Rect rect = new Rect();
        textPaint.getTextBounds(shigh, 0, shigh.length(), rect);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        rightOffSet = getFontWidth(textPaint, shigh);
        canvas.drawText(shigh, getWidth(), getFontHeight(textPaint) / 2, textPaint);
        for (int i = 1; i < yAxisNum + 1; i++) {
            String s = FormatUtil.format((highestYAxis - percentAxisGap * i) * 100, 2) + "%";
            canvas.drawText(s, getWidth(), (i * (getHeight() - xAxisHeght) / yAxisNum) + getFontHeight(textPaint) / 2, textPaint);
        }
    }

    private void drawChartXAxisLines(Canvas canvas) {
        for (int i = 0; i < yAxisNum + 1; i++) {
            canvas.drawLine(0, i * (getHeight() - xAxisHeght) / yAxisNum, getWidth(), i * (getHeight() - xAxisHeght) / yAxisNum, textPaint);
        }
    }

    private void drawRedChartLine(Canvas canvas) {
        mPath.moveTo(computePx(0, redPoints.size()), computePy((float) redPoints.get(0).getValue()));
        for (int i = 1; i < redPoints.size(); i++) {
            Profits point = redPoints.get(i);
            mPath.lineTo(computePx(i, redPoints.size()), computePy((float) point.getValue()));
        }
        canvas.drawPath(mPath, chartRedLinePaint);
        mPath.reset();
    }

    private void drawBlueChartLine(Canvas canvas) {
        mPath.moveTo(computePx(0, bluePoints.size()), computePy((float) bluePoints.get(0).getValue()));
        for (int i = 1; i < bluePoints.size(); i++) {
            Profits point = bluePoints.get(i);
            mPath.lineTo(computePx(i, bluePoints.size()), computePy((float) point.getValue()));
        }
        canvas.drawPath(mPath, chartBlueLinePaint);
        mPath.reset();
    }

    protected float computePx(int xIndex, int totalPoints) {

        return ((getWidth() - rightOffSet-10) / (float) (totalPoints - 1)) * xIndex;
    }

    protected float computePy(float val) {
        return (float) ((highestYAxis - val) / (highestYAxis - lowestYAxis) * (getHeight() - xAxisHeght));
    }

    protected int computeXIndex(float px, int totalPoints) {
        int xIndex = (int) (px / (getWidth() / (float) (totalPoints - 1)));
        return xIndex;
    }

    public void calculateDatas() {
        for (int i = 0; i < redPoints.size(); i++) {
            double yield = redPoints.get(i).getValue();
            highestPoint = Math.max(highestPoint, yield);
            lowestPoint = Math.min(lowestPoint, yield);
        }

        for (int i = 0; i < bluePoints.size(); i++) {
            double yield = bluePoints.get(i).getValue();
            highestPoint = Math.max(highestPoint, yield);
            lowestPoint = Math.min(lowestPoint, yield);
        }
        double a = Math.abs(highestPoint - lowestPoint);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (a > 0 && a < 0.0375) {
                percentAxisGap = 0.0125;
                break;
            }
            if (a >= 0.0375 * Math.pow(2, i) && a < 0.0375 * Math.pow(2, i + 1)) {
                percentAxisGap = 0.0125 * Math.pow(2, i + 1);
                break;
            }
        }
        highestYAxis = ((int) (highestPoint / percentAxisGap) + 1) * percentAxisGap;
        lowestYAxis = ((int) (lowestPoint / percentAxisGap) - 1) * percentAxisGap;
        yAxisNum = (int) (Math.abs(highestYAxis - lowestYAxis) / percentAxisGap);
    }


    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }


    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return -fm.ascent - fm.descent;
    }
}
