package com.alexm.chess960.clockpack

import com.alexm.chess960.ChessColor


/**
 * Created by alexm on 06/10/2019
 */
class Clock(val color: ChessColor, var timer: Long, var increment: Int) {

    fun tick(): Long {
        if (timer > 0) {
            timer--
        }
        return timer
    }

    fun addIncrement() {
        timer += increment
    }
}