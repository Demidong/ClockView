package com.xd.demi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.xd.demi.R;
import com.xd.demi.view.OvalProgrossView;

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
