package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle

import com.xd.demi.R
import kotlinx.android.synthetic.main.activity_jni.*

/**
 * Created by demi on 2019/2/27 上午9:49.
 */
class HelloJNIActivity : Activity() {
    // 步骤1:加载生成的so库文件
    // 注意要跟.so库文件名相同
    companion object {
        init {
            System.loadLibrary("hello_jni")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        click_jni.setOnClickListener {
            click_jni.text = getFromJNI()
        }
    }

    // 步骤2:定义在JNI中实现的方法

    external fun getFromJNI(): String
}
