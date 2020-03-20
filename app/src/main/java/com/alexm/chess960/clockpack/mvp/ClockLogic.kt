package com.alexm.chess960.clockpack.mvp

import com.alexm.chess960.clockpack.vo.Clock
import com.alexm.chess960.clockpack.ClockPreferences
import com.alexm.chess960.clockpack.vo.ClockSetting
import com.alexm.chess960.secondsToHMS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Completable.complete
import io.reactivex.rxjava3.core.Completable.defer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by alexm on 21/05/2018.
 */
internal class ClockLogic {

    fun getStoredSettings(prefs: ClockPreferences): Single<ClockSetting> = Single.defer {
        Single.just(
                ClockSetting(prefs.timeControl, prefs.timeIncrement)
        )
    }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    fun tick(runningClock: Clock): Observable<String> {
        return Observable.interval(300, 1000,
                TimeUnit.MILLISECONDS, Schedulers.computation())
                .map { runningClock.tick().secondsToHMS() }
                .take(runningClock.timer)
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun addIncrement(runningClock: Clock): Completable {
        return defer {
            runningClock.addIncrement()
            complete()
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}