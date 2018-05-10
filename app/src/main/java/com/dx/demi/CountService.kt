package com.dx.demi

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils

/**
 * Created by demi on 2018/5/9.
 */

class CountService : Service() {
    val TAG = "MyService"
    var num = 0
    override fun onCreate() {
        super.onCreate()
        num++
        LogUtils.d(TAG, "onCreate: $num")
    }

    override fun onBind(intent: Intent): IBinder? {
        LogUtils.d(TAG, "onBind :$num")
        val param = intent.extras.getInt("num")
        for (i in 1..10) {
            num += param
            LogUtils.d(TAG, "for  :$num")
        }
        return MyBinder()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LogUtils.d(TAG, "onStartCommand")
        val param = intent.extras.getInt("num")
        for (i in 1..10) {
            num += param
            LogUtils.d(TAG, "for  :$num")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        LogUtils.d(TAG, "onDestroy  :$num")
        super.onDestroy()
    }


    override fun onRebind(intent: Intent?) {
        LogUtils.d(TAG, "onRebind :$num")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtils.d(TAG, "onUnbind :$num")
        return super.onUnbind(intent)
    }

    override fun unbindService(conn: ServiceConnection?) {
        LogUtils.d(TAG, "unbindService :$num")
        super.unbindService(conn)
    }

    inner class MyBinder : Binder() {
        //此方法是为了可以在Acitity中获得服务的实例
        val service: CountService
            get() = this@CountService
    }
}
