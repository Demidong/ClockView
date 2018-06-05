package com.xd.demi.activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.xd.demi.R
import com.xd.demi.fragment.AFragment

/**
 * Created by demi on 2018/5/18.
 */

class TwoActivity : FragmentActivity() {
    private val TAG = "Activity生命周期"

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "TwoActivity onCreate")
        setContentView(R.layout.activity_second)
        supportFragmentManager.beginTransaction().add(R.id.frame, AFragment()).commit()

    }

    protected override fun onStart() {
        super.onStart()
        Log.i(TAG, "TwoActivity onStart")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i(TAG, "TwoActivity onWindowFocusChanged")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "TwoActivity onConfigurationChanged")
    }

    protected override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "TwoActivity onRestoreInstanceState")
    }

    protected override fun onResume() {
        super.onResume()
        Log.i(TAG, "TwoActivity onResume")
    }

    protected override fun onPause() {
        super.onPause()
        Log.i(TAG, "TwoActivity onPause")
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "TwoActivity onSaveInstanceState")
    }

    protected override fun onStop() {
        super.onStop()
        Log.i(TAG, "TwoActivity onStop")
    }

    protected override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "TwoActivity onDestroy")
    }
}
