package com.alexm.chess960

/**
 * Created by alexm on 03/06/2018.
 */

fun Int.secondsToHMS(): String {
    val hours: Int = this / 3600
    val minutes: Int = (this - hours * 3600) / 60
    val seconds: Int = this - (hours * 3600 + minutes * 60)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

enum class RunningClock {
    NONE,
    CLOCK_1,
    CLOCK_2
}

enum class PausePlayState {
    IDLE,
    PAUSE,
    PLAY
}
