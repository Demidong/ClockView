package com.xd.demi.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.xd.demi.R
import java.lang.reflect.Field

/**
 * Created by demi on 2018/5/15.
 */
class TabLayoutView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TabLayout(context, attrs, defStyleAttr) {

    private var mTabNames: List<String>? = null

    private val mViewHolders = ArrayList<ViewHolder>()

    private var mColorStateList: ColorStateList? = null

    private var mTextSize: Float = 0f

    //指示器宽度
    private var mIndicatorWidth: Int = 0

    private var mSelectedTextBold: Boolean = false

    private var mDefaultSelectedTab: Int = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutView)
        mColorStateList = ta.getColorStateList(R.styleable.TabLayoutView_tlv_text_color)
        mTextSize = ta.getDimensionPixelSize(R.styleable.TabLayoutView_tlv_text_size, 0).toFloat()
        mIndicatorWidth = ta.getDimensionPixelSize(R.styleable.TabLayoutView_tlv_indicator_width, 0)
        mSelectedTextBold = ta.getBoolean(R.styleable.TabLayoutView_tlv_selectedTextBold, false)
        ta.recycle()
    }

    fun setTabNames(vararg names: String) {
        val stringArray = names as Array<String>
        setTabNames(stringArray.toList())
    }

    /**
     * 设置Tab名字
     *
     * @param names
     */
    fun setTabNames(names: List<String>) {
        removeAllTabs()
        mViewHolders.clear()
        this.mTabNames = names
        for (i in names.indices) {
            val tab0 = newTab().setCustomView(getCustomView())
            if (i == mDefaultSelectedTab) {
                this.addTab(tab0, true)
            } else {
                this.addTab(tab0)
            }
            mViewHolders[i].tv_tab_name.text = names[i]
        }
        if (mSelectedTextBold) {
            mViewHolders[0].tv_tab_name.typeface = Typeface.DEFAULT_BOLD
        }
        postIndicatorValid()
    }

//    /**
//     * 设置对应的Tab的信息，包含的Item个数
//     *
//     * @param position
//     * @param count
//     */
//    fun setTabShowedCount(position: Int, count: Int) {
//        if (position >= mViewHolders.size) {
//            return
//        }
//        var format = "${mTabNames?.get(position)}($count)"
//        if (count == 0) {
//            format = mTabNames?.get(position) ?: ""
//        }
//        mViewHolders[position].tv_tab_name.text = format
//    }

//    fun showUnReadCount(showUnReadCount: Boolean, position: Int, unReadAllCount: Int) {
//        mViewHolders[position].mTvRedMsg.visibility = if (showUnReadCount) View.VISIBLE else View.GONE
//    }
//
//    private fun initView() {
//        val tab0 = newTab().setCustomView(getCustomView())
//        this.addTab(tab0)
//        if (mSelectedTextBold) {
//            mViewHolders[0].tv_tab_name.typeface = Typeface.DEFAULT_BOLD
//        }
//        val tab1 = this.newTab().setCustomView(getCustomView())
//        this.addTab(tab1)
//
//        postIndicatorValid()
//    }


    private fun postIndicatorValid() {
        this.post {
            var margin = 40
            try {
                if (mIndicatorWidth != 0) {
                    var width = width
                    if (tabCount != 0) {
                        width = getWidth() / tabCount
                    }
                    if (width != 0) {
                        margin = (width - mIndicatorWidth) / 2
                    }
                    if (margin > 0) {
                        setIndicator(this, margin, margin, TypedValue.COMPLEX_UNIT_PX)
                    }
                } else {
                    setIndicator(this, margin, margin, TypedValue.COMPLEX_UNIT_PX)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                setIndicator(this, margin, margin, TypedValue.COMPLEX_UNIT_DIP)
            }
        }
    }

    private fun setIndicator(tabs: TabLayoutView, leftDip: Int, rightDip: Int, unit: Int) {
        val tabLayout = tabs.javaClass.superclass as Class<*>
        val tabStrip: Field
        tabStrip = tabLayout.getDeclaredField("mTabStrip")

        tabStrip.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip.get(tabs) as LinearLayout
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val left = TypedValue.applyDimension(unit, leftDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
        val right = TypedValue.applyDimension(unit, rightDip.toFloat(), Resources.getSystem().displayMetrics).toInt()

        for (i in 0 until llTab!!.childCount) {
            val child = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            params.leftMargin = left
            params.rightMargin = right
            child.layoutParams = params
            child.invalidate()
        }
    }

    private fun getViewHolders(): List<ViewHolder> {
        return mViewHolders
    }

    private fun isSelectedTextBold(): Boolean {
        return mSelectedTextBold
    }

    fun setDefaultSelectedTab(tabPosition: Int) {
        this.mDefaultSelectedTab = tabPosition
    }

    private fun getCustomView(): View {
        val cusTomeView = View.inflate(context, R.layout.view_tab_layout, null)
        val viewHolder = ViewHolder(cusTomeView)
        mViewHolders.add(viewHolder)
        if (mColorStateList != null) {
            viewHolder.tv_tab_name.setTextColor(mColorStateList)
        }
        if (mTextSize != 0f) {
            viewHolder.tv_tab_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
        }
        return cusTomeView
    }

    abstract class OnTabSelectedListener(private val tabLayoutView: TabLayoutView) : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            if (tabLayoutView.isSelectedTextBold()) {
                tabLayoutView.getViewHolders()[tab.position].tv_tab_name.typeface = Typeface.DEFAULT_BOLD
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            if (tabLayoutView.isSelectedTextBold()) {
                tabLayoutView.getViewHolders()[tab.position].tv_tab_name.typeface = Typeface.DEFAULT
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            if (tabLayoutView.isSelectedTextBold()) {
                tabLayoutView.getViewHolders()[tab.position].tv_tab_name.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }

    private class ViewHolder internal constructor(view: View) {
        internal var tv_tab_name: TextView = view.findViewById(R.id.tv_tab_name)
        //internal var mTvRedMsg: TextView = view.findViewById(R.id.tv_red_msg)
    }
}