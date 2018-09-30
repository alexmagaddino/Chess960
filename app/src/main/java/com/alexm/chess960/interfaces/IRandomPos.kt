package com.alexm.chess960.interfaces

/**
 * Created by alexm on 21/05/2018.
 */

interface IRandomPos {
    fun shuffle()
    fun getPezzo(i: Int): String
}
