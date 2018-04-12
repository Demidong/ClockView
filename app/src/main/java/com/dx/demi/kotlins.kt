package com.dx.demi

/**
 * Created by demi on 2018/4/9.
 *
 */
fun main(args: Array<String>) {
    makeCount()
    makeCount()
    makeCount()
    makeCount()
    makeCount()
}

fun add(x: Int):()->Int{
    var h = 0
    h++
    return fun():Int{
       return  x+h
    }
}
fun makeCount():()-> Unit{
    var count = 0
    return fun(){
        println(++count)
    }
}