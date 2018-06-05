package com.xd.demi.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.xd.demi.R


/**
 * Created by demi on 2018/4/10.
 */
class ImageMatrixView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, def: Int = 0) : View(context, attrs, def) {
    var bitMap: Bitmap? = null
    var pointOneX = 0f
    var pointOneY = 0f
    var pointTwoX = 0f
    var pointTwoY = 0f
    var pointThreeX = 0f
    var pointThreeY = 0f
    var pointFourX = 0f
    var pointFourY = 0f
    var dx = 0f
    var dy = 0f
    var paint: Paint? = null
    val mtx = Matrix()

    init {
        bitMap = BitmapFactory.decodeResource(resources, R.mipmap.sky)
        pointTwoY = bitMap!!.height.toFloat()
        pointThreeX = bitMap!!.width.toFloat()
        pointThreeY = bitMap!!.height.toFloat()
        pointFourX = bitMap!!.width.toFloat()
        paint = Paint()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mtx.reset()
        val bw = bitMap!!.width
        val bh = bitMap!!.height
        val src = floatArrayOf(0f, 0f, 0f, bh.toFloat(), bw.toFloat(), bh.toFloat(), bw.toFloat(), 0f)
        val dst = floatArrayOf(pointOneX, pointOneY, pointTwoX, pointTwoY, pointThreeX, pointThreeY, pointFourX, pointFourY)
        println("dx: $pointOneX, $pointOneY, $pointTwoX, $pointTwoY, $pointThreeX, $pointThreeY, $pointFourX, $pointFourY")
        mtx.setPolyToPoly(src, 0, dst, 0, 4)
        mtx.postScale(0.15f, 0.15f)
        canvas.drawBitmap(bitMap, mtx, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        println("dx: ${event.action}")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dx = event.x
                dy = event.y
            }
            MotionEvent.ACTION_MOVE -> {

                if (Math.abs(dx - pointOneX * 0.15) < 100 && Math.abs(dy - pointOneY * 0.15) < 100) {
                    pointOneX = event.x * 100 / 15
                    pointOneY = event.y * 100 / 15
                }
                if (Math.abs(dx - pointTwoX * 0.15) < 100 && Math.abs(dy - pointTwoY * 0.15) < 100) {
                    pointTwoX = event.x * 100 / 15
                    pointTwoY = event.y * 100 / 15
                }
                if (Math.abs(dx - pointThreeX * 0.15) < 100 && Math.abs(dy - pointThreeY * 0.15) < 100) {
                    pointThreeX = event.x * 100 / 15
                    pointThreeY = event.y * 100 / 15

                }
                if (Math.abs(dx - pointFourX * 0.15) < 100 && Math.abs(dy - pointFourY * 0.15) < 100) {
                    pointFourX = event.x * 100 / 15
                    pointFourY = event.y * 100 / 15
                }
                invalidate()
                dx = event.x
                dy = event.y
            }
        }

        return true
    }

}

