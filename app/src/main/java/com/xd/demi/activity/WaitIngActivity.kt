package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import com.xd.demi.R
import kotlinx.android.synthetic.main.layout_waiting.*

/**
 * Created by demi on 2019/2/18 下午5:37.
 */
class WaitIngActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_waiting)
        start.setOnClickListener {
            floatWaitView.startMoving()
            loading.startMoving()
            wait.startMoving()
        }
        stop.setOnClickListener {
            floatWaitView.stopMoving()
            loading.stopMoving()
            wait.stopMoving()
        }
    }
}