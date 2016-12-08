package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.View.EventView;

/**
 * Created by demi on 16/12/6.
 */

public class EventActivity extends Activity {
    private float x =0,y =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView tv = (TextView) findViewById(R.id.tv);
        EventView eventView = (EventView) findViewById(R.id.event);
        eventView.setTextView(tv);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("activty dispatchTouchEvent..."+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("activty onTouchEvent..."+event.getAction());
        return super.onTouchEvent(event);
    }

}
