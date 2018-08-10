package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import android.view.View

import com.xd.demi.R
import kotlinx.android.synthetic.main.activity_scroller.*

/**
 * Created by demi on 2018/7/4 上午11:06.
 * Scroller 只滑动View的内容。
 */
class ScrollerActivity : Activity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller)
        tv_to.setOnClickListener(this)
        tv_by.setOnClickListener(this)
        tv_reset.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_to ->{
                notice.scrollTo(-300,0)
            }
            R.id.tv_by ->{
                notice.scrollBy(20,0)
            }
            R.id.tv_reset ->{
                notice.scrollTo(0,0)
            }
        }
    }
}
