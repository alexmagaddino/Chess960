package com.alexm.chess960.clockpack

import android.content.Context


/**
 * Created by alexm on 19/03/2020
 */
class ClockPreferences(private val context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "Clock_preferences"
        private const val TIME_CONTROL = "TIME_CONTROL"
        private const val TIME_INCREMENT = "TIME_INCREMENT"
    }

    private val preferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var timeControl: Long = 300
        get() = preferences.getLong(TIME_CONTROL, 300L)
        set(value) {
            preferences.edit().putLong(TIME_CONTROL, value).apply()
            field = value
        }

    var timeIncrement: Long = 0
        get() = preferences.getLong(TIME_INCREMENT, 0L)
        set(value) {
            preferences.edit().putLong(TIME_INCREMENT, value).apply()
            field = value
        }

}