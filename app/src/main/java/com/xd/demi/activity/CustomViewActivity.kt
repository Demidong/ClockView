package com.xd.demi.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xd.demi.R
import com.xd.demi.activity.MPChart.LineChartActivity
import com.xd.demi.adapter.DividerItemDecoration
import com.xd.demi.adapter.SamplesAdapter
import com.xd.demi.bean.Samples
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class CustomViewActivity : Activity() {
    private var clazz = arrayOf(InviteCodeActivity::class.java, WaitIngActivity::class.java, TrendActivity::class.java, LoveActivity::class.java, ScrollerActivity::class.java, ViewDragHelperActivity::class.java, LineChartActivity::class.java, PieChatActivity::class.java, ClockActivity::class.java, CoordinateActivity::class.java, ShopCarActivity::class.java, CameraActivity::class.java, TimeConutDownActivity::class.java, ViewPagerActivity::class.java, PercentActivity::class.java, WaveActivity::class.java, SelfDefinedActivity::class.java, FlowLayoutActivity::class.java, InputEmojiActivity::class.java)
    private var describle = arrayOf("InviteCodeActivity", "WaitIngActivity", "TrendActivity", "LoveActivity", "Scroller", "ViewDragHelper", "LineChartActivity", "水果大拼盘", "时钟表盘", "坐标系", "购物车动画", "照相机", "倒计时", "ViewPager", "圆环百分比", "雷达波浪View", "SelfDefinedActivity", "FlowLayoutActivity", "InputEmojiActivity")
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
            startActivity(Intent(this@CustomViewActivity, sample.mClass))
        }
    }
}
