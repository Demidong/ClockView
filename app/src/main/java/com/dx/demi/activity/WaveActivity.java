package com.dx.demi.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.dx.demi.R;
import com.dx.demi.view.WaveView;

/**
 * Created by demi on 2017/5/27.
 */

public class WaveActivity extends Activity {
    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        final WaveView wave = (WaveView) findViewById(R.id.wave);
        wave.start();
    }
}
