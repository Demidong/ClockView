package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.xd.demi.R
import kotlinx.android.synthetic.main.activity_producer_consumer.*

/**
 * Created by demi on 2018/11/13 上午11:24.
 */
class ProduceConsumerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producer_consumer)
        wave.setOnClickListener {
            launchRunnerble()
        }
    }

    private fun launchRunnerble(){
        val stack = SyncStack()
        val consumerThread = Thread(Consumer(stack),"消费者")
        val producerThread = Thread(Producer(stack),"生产者")
        producerThread.start()
        consumerThread.start()
    }
}

class Apple(val id: Int){
    override fun toString(): String {
        return "Apple id : $id"
    }
}


class Producer(val stack: SyncStack) : Runnable {
    override fun run() {
        for (i in 0..50) {
            val apple = Apple(i)
            stack.push(apple)
            Thread.sleep(1000)
        }
    }
}


class Consumer(val stack: SyncStack) : Runnable {
    override fun run() {
        for (i in 0..50) {
            Thread.sleep(3000)
            stack.pop()
        }
    }
}

class SyncStack {
    var num: Int = 0
    var maxNum = 10
    private  val  lock = Object()
    var appleStack: ArrayList<Apple> = arrayListOf()
    fun push(apple: Apple) {
        synchronized(lock){
            if (num == maxNum) {
                lock.wait()
            }
            lock.notify()
            num++
            Log.e("ProduceConsumerActivity", "已经生产 第$apple 个apple,篮子中剩余 $num 个")
            appleStack.add(apple)
        }


    }

     fun pop(): Apple {
        synchronized(lock){
            if (num == 0) {
                lock.wait()
            }
            lock.notify()
            num--
           val apple  = appleStack.removeAt(num)
            Log.e("ProduceConsumerActivity", "已经消费 第$apple 个apple,篮子中剩余 $num 个")
            return apple
        }
    }
}