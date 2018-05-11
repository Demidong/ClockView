package com.dx.demi

import java.io.File

/**
 * Created by demi on 2018/4/9.
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
