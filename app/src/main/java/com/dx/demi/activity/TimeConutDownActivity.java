package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.dx.demi.R;
import com.dx.demi.view.TimeCountDownView;
import com.mic.etoast2.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by demi on 17/1/20.
 */

public class TimeConutDownActivity extends Activity {
    CountdownView countdown ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdown = (CountdownView) findViewById(R.id.countdown);
        countdown.start(18*60*1000);
        countdown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                Toast.makeText(TimeConutDownActivity.this,"时间到！",2000).show();
            }
        });
    }
}
