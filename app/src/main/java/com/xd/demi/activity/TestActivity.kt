package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import com.xd.demi.R

/**
 * Created by demi on 2019/3/4 下午4:13.
 */
class TestActivity :Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}