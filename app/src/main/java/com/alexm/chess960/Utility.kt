package com.alexm.chess960

import java.util.concurrent.TimeUnit.*

/**
 * Created by alexm on 03/06/2018.
 */
fun <T : Number> T.secondsToHMS(): String {
    val hours: Long = SECONDS.toHours(this.toLong())
    val minutes: Long = SECONDS.toMinutes(this.toLong()) - HOURS.toMinutes(hours)
    val seconds: Long = this.toLong() - MINUTES.toSeconds(minutes) - HOURS.toSeconds(hours)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

enum class PausePlayState {
    IDLE,
    PAUSE,
    PLAY;
}

enum class ChessColor {
    WHITE,
    BLACK;

    fun isWhite() = this == WHITE

    operator fun not() = if (this.isWhite()) BLACK else WHITE
}