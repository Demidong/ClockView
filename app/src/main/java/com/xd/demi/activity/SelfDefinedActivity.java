package com.xd.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.xd.demi.R;
import com.xd.demi.view.CollapsExpandLayout;

/**
 * Created by demi on 16/11/28.
 */

public class SelfDefinedActivity extends Activity {
    CollapsExpandLayout collaps_view ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfdefined);
        collaps_view = (CollapsExpandLayout) findViewById(R.id.collaps_view);
        collaps_view.setName("大势已去");
        ImageView imageView = new ImageView(SelfDefinedActivity.this);
        imageView.setImageResource(R.mipmap.sky);
        collaps_view.setContent(imageView);
    }
}
