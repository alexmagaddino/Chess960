package com.alexm.chess960.clockPack

import com.alexm.chess960.interfaces.ClockContract
import com.alexm.chess960.secondsToHMS
import io.reactivex.Observable
import io.reactivex.Observable.defer
import io.reactivex.Observable.just
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by alexm on 21/05/2018.
 */

internal class ClockLogic : ClockContract.ILogic {

    var timeClock1: Int = 0
    var timeClock2: Int = 0
    var increment1: Int = 0
    var increment2: Int = 0

    override fun addIncrement1():  Observable<String> {
        return defer({
            timeClock1 += increment1
            return@defer just("All green")
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addIncrement2(): Observable<String> {
        return defer({
            timeClock2 += increment2
            return@defer just("All green")
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun setTimeControls(timeControl1: Int, timeControl2: Int, timeInc1: Int, timeInc2: Int) {
        this.timeClock1 = timeControl1
        this.timeClock2 = timeControl2
        this.increment1 = timeInc1
        this.increment2 = timeInc2
    }

    override fun tick1(): Observable<String> {
        return Observable.interval(300, 1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .map { (timeClock1--).secondsToHMS() }
                .take((timeClock1 + 1).toLong())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun tick2(): Observable<String> {
        return Observable.interval(300, 1000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .map { (timeClock2--).secondsToHMS() }
                .take((timeClock2 + 1).toLong())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}