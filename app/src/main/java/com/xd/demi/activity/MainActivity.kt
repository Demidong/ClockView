package com.xd.demi.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xd.demi.R
import com.xd.demi.adapter.DividerItemDecoration
import com.xd.demi.adapter.SamplesAdapter
import com.xd.demi.bean.Samples
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : Activity() {
    private var clazz = arrayOf(TestActivity::class.java, CustomViewActivity::class.java, ProduceConsumerActivity::class.java, ScreenMatchActivity::class.java, FirstActivity::class.java, MyServiceActivity::class.java, MatrixActivity::class.java, CoordinateActivity::class.java, EventActivity::class.java, ListActivity::class.java, NotifyCationActivity::class.java, EToastActivity::class.java, AnimationActivity::class.java, DownloadActivity::class.java, RetrofitOKHttpActivity::class.java, RxJavaActivity::class.java, PortfoliosActivity::class.java, VideoPlayerActivity::class.java)
    private var describle = arrayOf("临时测试Activity", "自定义View", "ProducerConsumer", "屏幕适配", "Activity生命周期", "MyServiceActivity", "Matrix", "坐标系", "事件监听", "多布局RecycleView列表", "通知栏事件监听", "自定义Toast", "动画", "下载apk", "Retrofit", "RXJava", "收益走势图", "视频播放器")
    private var sampleList = ArrayList<Samples>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in describle.indices) {
            val mSample = Samples()
            mSample.description = describle[i]
            mSample.mClass = clazz[i]
            sampleList.add(mSample)
        }
        val sampleAdapter = SamplesAdapter(sampleList, this)
        sampleAdapter.openLoadAnimation()
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = sampleAdapter
        rcv.addItemDecoration(DividerItemDecoration(this))
        sampleAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->
            val sample = adapter.getItem(position) as Samples
            startActivity(Intent(this@MainActivity, sample.mClass))
        }
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

}
