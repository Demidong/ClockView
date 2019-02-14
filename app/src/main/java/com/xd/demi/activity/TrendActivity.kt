package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import com.xd.demi.R
import kotlinx.android.synthetic.main.activity_trend.*

/**
 * Created by demi on 2018/12/6 下午5:35.
 */
class TrendActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trend)
        change.setOnClickListener {
            val left = (Math.random() * 100).toInt()
            pv.setPercent(left, 100 - left)
        }
    }
}