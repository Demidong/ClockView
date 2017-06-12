package com.dx.demi.activity;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.dx.demi.R;
import com.dx.demi.view.WaveView;

import java.io.IOException;

/**
 * Created by demi on 2017/5/27.
 */

public class WaveActivity extends Activity {
    private ImageView head;
    private WaveView wave;
    private ScaleAnimation scaleAnimation;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        scaleAnimation = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        wave = (WaveView) findViewById(R.id.wave);
        head = (ImageView) findViewById(R.id.head);
        mPlayer = MediaPlayer.create(this, R.raw.water_wave);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave.addWave();
                head.startAnimation(scaleAnimation);
                if(mPlayer.isPlaying()){
                    mPlayer.stop();
                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mPlayer.start();
            }
        });
        wave.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wave.setImageRadius(head.getWidth()/2);
    }
}
