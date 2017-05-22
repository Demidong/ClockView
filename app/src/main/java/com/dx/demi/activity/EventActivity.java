package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.view.EventView;

/**
 * Created by demi on 16/12/6.
 */

public class EventActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView tv = (TextView) findViewById(R.id.tv);
        EventView eventView = (EventView) findViewById(R.id.event);
        eventView.setTextView(tv);
//        eventView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("eventView onClick...");
//            }
//
//            });
//        eventView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("eventView onTouch..."+event.getAction());
//                return true;
//            }
//        });

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
