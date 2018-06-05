package com.xd.demi

import android.app.Application
import android.content.Context
import com.xd.demi.BuildConfig
import com.exyui.android.debugbottle.components.DTInstaller

/**
 * Created by demi on 2018/5/11.
 */
class DemiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDebugTool()
    }

    private fun initDebugTool() {
        if (BuildConfig.DEBUG) {
            getSharedPreferences("dt_settings", Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("BOTTLE_ENABLE", true)
                    .putBoolean("NETWORK_SNIFF", true)
                    .putBoolean("BLOCK_CANARY_ENABLE", true)
                    .putBoolean("LEAK_CANARY_ENABLE", true)
                    .apply()
            DTInstaller.install(this)
                    .setBlockCanary(AppBlockCanaryContext(this))
                    //.setOkHttpClient(RetrofitManager.client)
                   // .setInjector(ContentInjector())
                   // .setPackageName("com.dx.demi")
                    .enable()
                    .run()
        }
    }
}