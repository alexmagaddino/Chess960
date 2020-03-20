package com.alexm.chess960.clockpack.dialog

import com.alexm.chess960.clockpack.ClockPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Completable.complete
import io.reactivex.rxjava3.core.Completable.defer
import io.reactivex.rxjava3.schedulers.Schedulers


class ClockSettingLogic(private val prefs: ClockPreferences) {
    fun setClock(timeControl: Long, timeIncrement: Long): Completable = defer {
        prefs.timeControl = timeControl
        prefs.timeIncrement = timeIncrement
        complete()
    }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}