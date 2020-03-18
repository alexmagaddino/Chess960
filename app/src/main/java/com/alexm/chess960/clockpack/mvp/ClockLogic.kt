package com.alexm.chess960.clockpack.mvp

import com.alexm.chess960.clockpack.Clock
import com.alexm.chess960.secondsToHMS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.defer
import io.reactivex.rxjava3.core.Observable.just
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by alexm on 21/05/2018.
 */
internal class ClockLogic {

    private val clock1 = Clock(0, 0)
    private val clock2 = Clock(0, 0)

    fun addIncrement1(): Observable<String> {
        return defer {
            clock1.addIncrement()
            return@defer just("All green")
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun addIncrement2(): Observable<String> {
        return defer {
            clock2.addIncrement()
            return@defer just("All green")
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun setTimeControls(timeControl1: Int, timeControl2: Int, inc1: Int, inc2: Int): Completable {
        return Completable.defer {
            clock1.apply {
                setTimer(timeControl1)
                setIncrement(inc1)
            }
            clock2.apply {
                setTimer(timeControl2)
                setIncrement(inc2)
            }

            Completable.complete()
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun tick1(): Observable<String> {
        return Observable.interval(300, 1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .map { clock1.tick().secondsToHMS() }
                .take(clock1.getTime())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun tick2(): Observable<String> {
        return Observable.interval(300, 1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .map { clock2.tick().secondsToHMS() }
                .take(clock2.getTime())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}