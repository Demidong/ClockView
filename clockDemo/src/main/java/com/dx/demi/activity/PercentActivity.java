package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.dx.demi.R;
import com.dx.demi.view.OvalProgrossView;

/**
 * Created by demi on 16/11/29.
 */

public class PercentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        OvalProgrossView oval= (OvalProgrossView) findViewById(R.id.oval);
        oval.setmOvalProgress(88);

    }
}
