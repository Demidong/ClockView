package com.dx.demi

import java.io.File

/**
 * Created by demi on 2018/4/9.
 * //需要下载安装IntelliJ IDEA 不然会报错
 *
 * Exception in thread "main" java.lang.ClassNotFoundException: com.dx.demi.KotlinsKt
at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
at java.lang.Class.forName0(Native Method)
at java.lang.Class.forName(Class.java:264)
at com.intellij.rt.execution.application.AppMainV2.main(AppMainV2.java:107)
 *
 */
    fun main(args: Array<String>) {
//    makeCount()
//    makeCount()
//    makeCount()
//    makeCount()
//    makeCount()
        listFile( File("/Users/demi/Documents/DAXIANG/ClockView"))
    }
    fun add(x: Int): () -> Int {
        var h = 0
        h++
        return fun(): Int {
            return x + h
        }
    }

    fun makeCount(): () -> Unit {
        var count = 0
        return fun() {
            println(++count)
        }

    }

    fun listFile(file: File) {
        if (file.isFile) {
            println(file.absolutePath)
            return
        }
        for (i in 0 until file.list()!!.size) {
            if (file.listFiles()!![i].isDirectory) {
                listFile(file)
            } else {
                println(file.absolutePath)
            }
        }

    }

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        for(i in nums.indices-1){
            if(nums[i]+nums[i+1]==target){
                return intArrayOf(i,i+1)
            }
        }
        return intArrayOf(0,1)
    }
}


