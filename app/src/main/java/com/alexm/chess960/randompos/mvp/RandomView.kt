package com.alexm.chess960.randompos.mvp

import com.alexm.chess960.randompos.RandomPos

/**
 * Created by alexm on 04/10/2019
 */
interface RandomView {
    fun showRandomPos(pos: RandomPos)
}