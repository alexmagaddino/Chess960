package com.alexm.chess960.randompos

import com.alexm.chess960.randompos.RandomPos.ChessPieces.*

/**
 * Created by alexm on 25/05/2018.
 */
class RandomPos {
    private val pezzi = values().toMutableList()

    enum class ChessPieces(val prettyString: String) {
        LEFT_ROOK("Torre sinistra"),
        KNIGHT_OF_QUEEN("Cavallo"),
        DARK_FIELD_BISHOP("Alfiere campo scuro"),
        QUEEN("Donna"),
        KING("Re"),
        BRIGHT_FIELD_BISHOP("Alfiere campo chiaro"),
        KNIGHT_OF_KING("Cavallo"),
        RIGHT_ROOK("Torre destra");
    }

    fun shuffle() {
        pezzi.shuffle()
        while (true) {
            if (pezzi.indexOf(KING) != 0 && pezzi.indexOf(KING) != 7 &&
                    pezzi.indexOf(LEFT_ROOK) < pezzi.indexOf(KING) &&
                    pezzi.indexOf(RIGHT_ROOK) > pezzi.indexOf(KING)
                    && pezzi.indexOf(BRIGHT_FIELD_BISHOP) % 2 == 1 &&
                    pezzi.indexOf(DARK_FIELD_BISHOP) % 2 == 0)
                break
            else
                pezzi.shuffle()
        }
    }

    fun getPezzo(i: Int): String {
        return when (pezzi[i]) {
            LEFT_ROOK, RIGHT_ROOK -> "Torre"
            DARK_FIELD_BISHOP, BRIGHT_FIELD_BISHOP -> "Alfiere"
            else -> pezzi[i].prettyString
        }
    }
}