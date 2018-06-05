package com.xd.demi.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.xd.demi.CountService
import com.xd.demi.R
import kotlinx.android.synthetic.main.activity_kot.*


/**
 * Created by demi on 2018/5/9.
 */

class MyServiceActivity : Activity(), ServiceConnection {
    /**
     * 只是用startService()启动服务：onCreate() -> onStartCommand() -> onDestory
    只是用bindService()绑定服务：onCreate() -> onBind() -> onUnBind() -> onDestory
    同时使用startService()启动服务与bindService()绑定服务：onCreate() -> onStartCommnad() -> onBind() -> onUnBind() -> onDestory
     */
    var isbind :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kot)
        val startIntent = Intent(this, CountService::class.java)
        startIntent.putExtra("num", 100)
        start.setOnClickListener {
            startService(startIntent)
        }
        bind.setOnClickListener {
            bindService(startIntent, this, Context.BIND_AUTO_CREATE)
        }
        stop.setOnClickListener {
            stopService(startIntent)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //onServiceDisconnected方法在正常情况下是不被调用的，它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时.
        LogUtils.d("MyServiceActivity", "onServiceDisconnected  :$name")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        //  onServiceConnected()方法在onBind()方法调用后调用，但前提是返回的IBinder不为NUll
        LogUtils.d("MyServiceActivity", "onServiceConnected  :$name")
        isbind = true
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isbind) unbindService(this)
    }

}
