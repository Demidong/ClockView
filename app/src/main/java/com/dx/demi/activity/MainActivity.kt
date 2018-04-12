//package com.dx.demi.activity
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.View
//
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.dx.demi.R
//import com.dx.demi.adapter.DividerItemDecoration
//import com.dx.demi.adapter.SamplesAdapter
//import com.dx.demi.bean.Samples
//import kotlinx.android.synthetic.main.activity_main.*
//
//import java.util.ArrayList
//
//
//class MainActivity : AppCompatActivity() {
//    internal var clazz = arrayOf<Class<*>>(PieChatActivity::class.java, ClockActivity::class.java, CoordinateActivity::class.java, EventActivity::class.java, ListActivity::class.java, NotifyCationActivity::class.java, ShopCarActivity::class.java, CameraActivity::class.java, EToastActivity::class.java, TimeConutDownActivity::class.java, AnimationActivity::class.java, ViewPagerActivity::class.java, DownloadActivity::class.java, RetrofitOKHttpActivity::class.java, PercentActivity::class.java, PortfoliosActivity::class.java)
//    internal var describe = arrayOf("水果大拼盘", "时钟表盘", "坐标系", "事件监听", "多布局RecycleView列表", "通知栏事件监听", "购物车动画", "照相机", "自定义Toast", "倒计时", "动画", "ViewPager", "下载apk", "Retrofit", "圆环百分比", "收益走势图")
//    internal var sampleList = ArrayList<Samples>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        for (i in describe.indices) {
//            val mSample = Samples()
//            mSample.description = describe[i]
//            mSample.mClass = clazz[i]
//            sampleList.add(mSample)
//        }
//        val sampleAdapter = SamplesAdapter(sampleList, this)
//        sampleAdapter.openLoadAnimation()
//        rcv.layoutManager = LinearLayoutManager(this)
//        rcv.adapter = sampleAdapter
//        rcv.addItemDecoration(DividerItemDecoration(this))
//        sampleAdapter.setOnItemChildClickListener { adapter, view, position ->
//            val sample = adapter.getItem(position) as Samples
//            startActivity(Intent(this@MainActivity, sample.mClass))
//            true
//        }
//    }
//
//
//}
