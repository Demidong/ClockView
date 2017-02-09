package com.dx.demi.View;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dx.demi.R;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by demi on 17/1/20.
 */

public class TimeCountDownView extends LinearLayout {
    TextView hour_text;
    TextView minute_text;
    TextView seconds_text;
    long deadTime ;
    DecimalFormat df = null;
    private Timer timer;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setTime();
        }
    };

    public TimeCountDownView(Context context) {
        this(context,null);
    }

    public TimeCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.timedown,this);
        hour_text= (TextView) view.findViewById(R.id.hour_text);
        minute_text= (TextView) view.findViewById(R.id.minute_text);
        seconds_text= (TextView) view.findViewById(R.id.seconds_text);
        df =new DecimalFormat("00");

    }

    public void setDeadTime(long deadTime) {
        this.deadTime = deadTime;
        setTime();
        startCountDown();
    }

    public void setTime(){
        long currentTime = System.currentTimeMillis();
        int time = (int) ((deadTime - currentTime)/1000);
        if(time == 0){
            Toast.makeText(getContext(),"时间到了",Toast.LENGTH_LONG).show();
            return;
        }
        int hour = time/60 < 60 ? 0:time/60/60 ;
        int minute = time/60 < 60 ? time/60 : time/60%60 ;
        int seconds = time%60 ;
        hour_text.setText(String.valueOf(df.format(hour)));
        minute_text.setText(String.valueOf(df.format(minute)));
        seconds_text.setText(String.valueOf(df.format(seconds)));

    }
    public void startCountDown(){
        timer= new Timer();
        TimerTask timerTask =new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,0,1000);
        timerTask.run();
    }
    public void finishCount(){
        handler =null;
        timer.purge();
        timer.cancel();
    }
}
