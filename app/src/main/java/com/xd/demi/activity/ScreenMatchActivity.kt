package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import com.xd.demi.R

/**
 * Created by demi on 2018/6/24 上午10:09.
 */

/**
 * 屏幕尺寸：称为英寸 inch 一英寸 = 2.54cm . 比如 mi note3 是 5.5inch ,是指手机对角线的距离
屏幕分辨率： 屏幕的宽和高的像素数，比如 mi note3 是 1920px *1080px
屏幕像素密度 ：每inch的像素数  比如 mi note3 是 401 ppi  px per inch
● px：pixel，像素，电子屏幕上组成一幅图画或照片的最基本单元
● pt: point，点，印刷行业常用单位，等于1/72英寸
● ppi: pixel per inch，每英寸像素数，该值越高，则屏幕越细腻
● dpi: dot per inch，每英寸多少点，该值越高，则图片越细腻
● dp: dip，Density-independent pixel, 是安卓开发用的长度单位，1dp表示在屏幕像素点密度为160ppi时1px长度
● sp: scale-independent pixel，安卓开发用的字体大小单位。
二、计算公式  :  屏幕分辨率 = 屏幕尺寸 * 屏幕像素密度   401 * 5.5 = 2205.5 px 斜对角线的像素数
1dp=（屏幕ppi/ 160）px
三、同一张图片 比如 50 px * 50px 的图片，放在不同的drawbles文件夹里,运行在同一部手机上。会有放大缩小的效果。
那么是如何方法缩小的呢？
我们的手机如果是 hdpi的，如果你这个图片放到了hdpi中，那这张图片就是50px,如果说你放到了xxhdpi中，那根据比例，hdpi :xxhdpi = 1：2 ，那就要缩小一倍，那就会缩小到25px.同理，把图片放到mdpi，你的图片会放大1.5倍。
四、同一张图片 比如 50 px * 50px 的图片 放在其中一种drawbles文件夹里比如mdpi，运行在不同分辨率的手机上，会对应的放大缩小

密度 ldpi mdpi hdpi xhdpi xxhdpi
比例 0.75 1 1.5 2 3
图片像素 37.5px 50px 75px 100px 150px
 */
class ScreenMatchActivity :Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_match)
    }
}