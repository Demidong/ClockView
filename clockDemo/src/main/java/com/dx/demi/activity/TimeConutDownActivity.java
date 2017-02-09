package com.dx.demi.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dx.demi.R;
import com.dx.demi.View.EToast;
import com.dx.demi.View.TimeCountDownView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by demi on 17/1/20.
 */

public class TimeConutDownActivity extends Activity {
    TimeCountDownView countdown ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdown = (TimeCountDownView) findViewById(R.id.countdown);
        countdown.setDeadTime(str2Time("2017-02-09 12:00:00"));
    }
    public static long str2Time(String str){
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTimeInMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countdown.finishCount();
    }
}
