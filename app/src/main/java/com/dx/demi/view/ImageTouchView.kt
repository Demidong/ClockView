package com.dx.demi.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView


@SuppressLint("AppCompatCustomView")
class ImageTouchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, def: Int = 0) : ImageView(context, attrs, def) {

    private val startPoint = PointF()
    private val mtx = Matrix()
    private val currentMaritx = Matrix()

    private var mode = 0//用于标记模式
    private var startDis = 0f
    private var midPoint: PointF? = null//中心点

init {
    scaleType = ScaleType.MATRIX
}
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mode = DRAG
                currentMaritx.set(this.imageMatrix)//记录ImageView当期的移动位置
                startPoint.set(event.x, event.y)//开始点
            }

            MotionEvent.ACTION_MOVE//移动事件
            -> {
                if (mode == DRAG) {//图片拖动事件
                    val dx = event.x - startPoint.x//x轴移动距离
                    val dy = event.y - startPoint.y//y轴移动距离
                    mtx.set(currentMaritx)//在当前的位置基础上移动
                    mtx.postTranslate(dx, dy)

                } else if (mode == ZOOM) {//图片放大事件
                    val endDis = distance(event)//结束距离
                    if (endDis > 10f) {
                        val scale = endDis / startDis//放大倍数
                        Log.v("scale=", scale.toString())
                        mtx.set(currentMaritx)
                        mtx.postScale(scale, scale, midPoint!!.x, midPoint!!.y)
                    }
                }
            }
            MotionEvent.ACTION_UP -> mode = 0
        //有手指离开屏幕，但屏幕还有触点(手指)
            MotionEvent.ACTION_POINTER_UP -> mode = 0
        //当屏幕上已经有触点（手指）,再有一个手指压下屏幕,变成放大模式,计算两点之间中心点的位置
            MotionEvent.ACTION_POINTER_DOWN -> {
                mode = ZOOM
                startDis = distance(event)//计算得到两根手指间的距离

                if (startDis > 10f) {//避免手指上有两个茧
                    midPoint = mid(event)//计算两点之间中心点的位置
                    currentMaritx.set(this.imageMatrix)//记录当前的缩放倍数
                }
            }
        }//##switch##
        imageMatrix = mtx
//        imageMatrix.set(mtx)
//        invalidate()
        return true
    }

    companion object {
        private val DRAG = 1//拖动
        private val ZOOM = 2//放大

        /**
         * 两点之间的距离
         * @param event
         * @return
         */
        private fun distance(event: MotionEvent): Float {
            //两根手指间的距离
            val dx = event.getX(1) - event.getX(0)
            val dy = event.getY(1) - event.getY(0)
            return Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        }

        /**
         * 计算两点之间中心点的位置
         * @param event
         * @return
         */
        private fun mid(event: MotionEvent): PointF {
            val midx = event.getX(1) + event.getX(0)
            val midy = event.getY(1) + event.getY(0)

            return PointF(midx / 2, midy / 2)
        }
    }

}  
