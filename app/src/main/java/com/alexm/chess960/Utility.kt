package com.alexm.chess960

import java.util.concurrent.TimeUnit

/**
 * Created by alexm on 03/06/2018.
 */

fun Int.secondsToHMS(): String {
    val hours: Long = TimeUnit.SECONDS.toHours(this.toLong())
    val minutes: Long = TimeUnit.SECONDS.toMinutes(this.toLong()) - TimeUnit.HOURS.toMinutes(hours)
    val seconds: Long = this - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours)
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
