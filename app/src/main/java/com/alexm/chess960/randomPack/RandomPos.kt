package com.alexm.chess960.randomPack

import com.alexm.chess960.interfaces.IRandomPos
import java.util.*

/**
 * Created by alexm on 25/05/2018.
 */

class RandomPos : IRandomPos {
    private val pezzi: ArrayList<String> = ArrayList(8)
    private val leftRook = "Torre sinistra"
    private val knight1 = "Cavallo"
    private val darkFieldBishop = "Alfiere campo scuro"
    private val queen = "Donna"
    private val king = "Re"
    private val brightFieldBishop = "Alfiere campo chiaro"
    private val knight2 = "Cavallo"
    private val rightRook = "Torre destra"

    init {
        pezzi.add(leftRook)
        pezzi.add(knight1)
        pezzi.add(darkFieldBishop)
        pezzi.add(queen)
        pezzi.add(king)
        pezzi.add(brightFieldBishop)
        pezzi.add(knight2)
        pezzi.add(rightRook)
    }

    override fun shuffle() {
        pezzi.shuffle()
        while (true) {
            if (pezzi.indexOf(king) != 0 && pezzi.indexOf(king) != 7 &&
                    pezzi.indexOf(leftRook) < pezzi.indexOf(king) &&
                    pezzi.indexOf(rightRook) > pezzi.indexOf(king)
                    && pezzi.indexOf(brightFieldBishop) % 2 == 1 &&
                    pezzi.indexOf(darkFieldBishop) % 2 == 0)
                break
            else
                pezzi.shuffle()
        }
    }

    override fun getPezzo(i: Int): String {
        return when (pezzi[i]) {
            leftRook, rightRook -> "Torre"
            darkFieldBishop, brightFieldBishop -> "Alfiere"
            else -> pezzi[i]
        }
    }
}