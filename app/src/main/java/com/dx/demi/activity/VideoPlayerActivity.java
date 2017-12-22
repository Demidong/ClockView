package com.dx.demi.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.demi.R;

import java.io.IOException;

/**
 * Created by demi on 2017/6/12.
 */

public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback{
    SurfaceView mSurfaceView;
    MediaPlayer mMediaPlayer;
    ImageView play;
    TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        play = (ImageView) findViewById(R.id.play);
        time = (TextView) findViewById(R.id.time);
        mMediaPlayer =new MediaPlayer();
        mSurfaceView.getHolder().addCallback(this);
        try {
            mMediaPlayer.setDataSource("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSurfaceView.getHolder().setKeepScreenOn(true);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();
                }
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                time.setText(mp.getDuration()+"");
                mMediaPlayer.start();
            }
        });

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
