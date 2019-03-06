package com.xd.demi.activity

import com.xd.demi.main
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by demi on 2019/3/6 上午11:04.
 */
class MainActivityTest {
    internal var main: MainActivity? = null
    @Before
    fun setUp() {
        main = MainActivity()
        println("Test Start")
    }

    @After
    fun tearDown() {
        main = null
        println("Test End")
    }

    @Test
    fun add() {
        assertEquals(188, main?.add(100, 88))
    }
}