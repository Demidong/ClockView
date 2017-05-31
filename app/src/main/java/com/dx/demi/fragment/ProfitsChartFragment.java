package com.dx.demi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dx.demi.R;
import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.utils.FormatUtil;
import com.dx.demi.view.ProfitsChartView;
import com.dx.demi.view.TabContainer;

import java.util.ArrayList;

/**
 * Created by demi on 2017/5/16.
 */

public class ProfitsChartFragment extends Fragment {
    private TabContainer tab ;
    private ProfitsChartView chart ;
    private TextView date_start ;
    private TextView date_end ;
    private TextView tv_one ;
    private TextView tv_two ;
    private TextView tv_three ;
    private TextView tv_four ;
    private TextView tv_five ;
    private double  highestPoint ;
    private double  lowestPoint ;
    private double  percentAxisGap ;
    private ArrayList<ListInfo<Profits>> info = new ArrayList<>();
    private ArrayList<Profits> redPoints  = new ArrayList<>();
    private ArrayList<Profits> bluePoints  = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit_chart, container, false);
        tab = (TabContainer) view.findViewById(R.id.tab);
        chart = (ProfitsChartView) view.findViewById(R.id.chart);
        date_start = (TextView) view.findViewById(R.id.date_start);
        date_end = (TextView) view.findViewById(R.id.date_end);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        tv_four = (TextView) view.findViewById(R.id.tv_four);
        tv_five = (TextView) view.findViewById(R.id.tv_five);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i <50 ; i++) {
            Profits redpoint =new Profits();
            Profits bluepoint =new Profits();
            redpoint.setTradeDay(System.currentTimeMillis()+i*30);
            redpoint.setYield(Math.random());
            bluepoint.setTradeDay(System.currentTimeMillis()+i*30);
            bluepoint.setYield(Math.random());
            redPoints.add(redpoint);
            bluePoints.add(bluepoint);
        }
        ListInfo<Profits> redListInfo = new ListInfo<>();
        redListInfo.setType("HS300");
        redListInfo.setTotal(50);
        redListInfo.setList(redPoints);
        ListInfo<Profits> blueListInfo = new ListInfo<>();
        blueListInfo.setType("PORTFOLIO");
        blueListInfo.setTotal(50);
        blueListInfo.setList(bluePoints);
        info.add(blueListInfo);
        info.add(redListInfo);
        getPercentAxisGap();
        chart.setData(info);
        setPercentText();
    }
    private void getPercentAxisGap(){
        for (int i = 0; i <redPoints.size(); i++) {
            double  yield = redPoints.get(i).getYield() ;
            highestPoint =  Math.max(highestPoint,yield);
            lowestPoint =  Math.min(lowestPoint,yield);
        }
        for (int i = 0; i <bluePoints.size(); i++) {
            double  yield = bluePoints.get(i).getYield();
            highestPoint =  Math.max(highestPoint,yield);
            lowestPoint =  Math.min(lowestPoint,yield);
        }
        percentAxisGap = (Math.abs(highestPoint)+ Math.abs(lowestPoint))/4;
        chart.setHighestPoint(highestPoint);
        chart.setLowestPoint(lowestPoint);
    }
   public void  setPercentText(){
       tv_one.setText(FormatUtil.format(highestPoint*100,2)+"%");
       tv_two.setText(FormatUtil.format((highestPoint-percentAxisGap)*100,2)+"%");
       tv_three.setText(FormatUtil.format((highestPoint-percentAxisGap*2)*100,2)+"%");
       tv_four.setText(FormatUtil.format((highestPoint-percentAxisGap*3)*100,2)+"%");
       tv_five.setText(FormatUtil.format(lowestPoint*100,2)+"%");
    }
}
