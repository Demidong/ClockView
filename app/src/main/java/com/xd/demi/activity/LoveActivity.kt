package com.xd.demi.activity

import android.app.Activity
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import com.xd.demi.R

/**
 * Created by demi on 2018/12/6 下午5:35.
 */
class LoveActivity : Activity() {

    var mediaPlayer: MediaPlayer? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love)
         mediaPlayer = MediaPlayer.create(this, R.raw.whys)
         mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }
}