package com.alexm.chess960.clockpack


/**
 * Created by alexm on 06/10/2019
 */
class Clock(private var timer: Int, private var increment: Int) {

    fun getTime(): Long = (timer + 1).toLong()

    fun setTimer(timer: Int) {
        this.timer = timer
    }

    fun setIncrement(increment: Int) {
        this.increment = increment
    }

    fun tick(): Int {
        if (timer > 0) {
            timer--
        }
        return timer
    }

    fun addIncrement() {
        timer += increment
    }
}