package com.dx.demi.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.blankj.utilcode.util.LogUtils
import com.dx.demi.R
import kotlinx.android.synthetic.main.activity_first.*

/**
 * Created by demi on 2018/5/18.
 */

class TwoActivity : Activity() {
    private val TAG = "Activity生命周期"

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "TwoActivity onCreate")
        setContentView(R.layout.activity_second)
        start.setOnClickListener{
            startActivity(Intent(this,FirstActivity::class.java))
        }
    }

    protected override fun onStart() {
        super.onStart()
        Log.i(TAG, "TwoActivity onStart")
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
