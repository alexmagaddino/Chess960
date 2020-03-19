package com.alexm.chess960.clockpack

import android.content.Context


/**
 * Created by alexm on 19/03/2020
 */
class ClockPreferences(private val context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "Clock_preferences"
        private const val WHITE_TIME = "White_time"
        private const val BLACK_TIME = "Black_time"
        private const val WHITE_INC = "White_inc"
        private const val BLACK_INC = "Black_inc"
    }

    private val preferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var whiteTime: Long = 300
        get() = preferences.getLong(WHITE_TIME, 300L)
        set(value) {
            preferences.edit().putLong(WHITE_TIME, value).apply()
            field = value
        }

    var blackTime: Long = 300
        get() = preferences.getLong(BLACK_TIME, 300L)
        set(value) {
            preferences.edit().putLong(WHITE_TIME, value).apply()
            field = value
        }

    var whiteInc: Long = 0
        get() = preferences.getLong(WHITE_INC, 0L)
        set(value) {
            preferences.edit().putLong(WHITE_INC, value).apply()
            field = value
        }

    var blackInc: Long = 0
        get() = preferences.getLong(BLACK_INC, 0L)
        set(value) {
            preferences.edit().putLong(BLACK_INC, value).apply()
            field = value
        }
}