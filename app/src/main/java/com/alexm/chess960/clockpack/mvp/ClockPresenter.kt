package com.alexm.chess960.clockpack.mvp

import com.alexm.chess960.PausePlayState
import com.alexm.chess960.RunningClock
import com.alexm.chess960.secondsToHMS
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Created by alexm on 21/05/2018.
 */

internal class ClockPresenter(private val logic: ClockLogic) {

    companion object {
        private const val FINISH_TEXT = "Tempo scaduto"
    }

    private var view: ClockView? = null
    private var setCountdownDisposer: Disposable? = null
    private val clockDisposers = mutableMapOf<RunningClock, Disposable?>()
    //this field need to know if is in pause or in play
    // and who was the last clock that runnning before the pause
    private var pauseOrPlay: Int = 0
    private var lastRunningClock: RunningClock = RunningClock.NONE


    fun startCountdown(clockSel: RunningClock) {

        if (clockSel == RunningClock.CLOCK_1) {
            logic.tick1().subscribe(object : Observer<String> {
                override fun onComplete() {
                    view?.showCountdown(FINISH_TEXT, clockSel)
                    view?.finishCountdown()
                }

                override fun onSubscribe(d: Disposable?) {
                    pauseClock2()
                    logic.addIncrement2().subscribe()
                    lastRunningClock = RunningClock.CLOCK_1
                    view?.enableButton1(true)
                    view?.enableButton2(false)
                    view?.enableHomeButton(false)
                    view?.enableRestartButton(false)
                    view?.enableSetButton(false)
                    view?.setPausePlayState(PausePlayState.PAUSE)
                    clockDisposers[clockSel] = d
                }

                override fun onNext(remainingTime: String) {
                    view?.showCountdown(remainingTime, clockSel)
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }
            })
        } else {
            logic.tick2().subscribe(object : Observer<String> {
                override fun onComplete() {
                    view?.showCountdown(FINISH_TEXT, clockSel)
                    view?.finishCountdown()
                }

                override fun onSubscribe(d: Disposable?) {
                    pauseClock1()
                    logic.addIncrement1().subscribe()
                    lastRunningClock = RunningClock.CLOCK_2
                    view?.enableButton1(false)
                    view?.enableButton2(true)
                    view?.enableHomeButton(false)
                    view?.enableRestartButton(false)
                    view?.enableSetButton(false)
                    view?.setPausePlayState(PausePlayState.PAUSE)
                    clockDisposers[clockSel] = d
                }

                override fun onNext(remainingTime: String) {
                    view?.showCountdown(remainingTime, clockSel)
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }
            })
        }
    }

    fun pauseClock1() {
        clockDisposers[RunningClock.CLOCK_1]?.dispose()
    }

    fun pauseClock2() {
        clockDisposers[RunningClock.CLOCK_2]?.dispose()
    }

    /*this method control the pauseOrPlay field:
    if is an even number pause clocks, blocks the buttons and save who was the last running clock.
    else enable the buttons and control who was the last running clock (0 none, 1 the first, 2 the second)
    at the end increase the pauseOrPlay to change state
    */
    fun pausePlay() {
        if (pauseOrPlay % 2 == 0) {
            pauseClock1()
            pauseClock2()
            view?.enableButton1(false)
            view?.enableButton2(false)
            view?.enableHomeButton(true)
            view?.enableSetButton(true)
            view?.enableRestartButton(true)
            view?.setPausePlayState(PausePlayState.PLAY)
        } else {
            view?.enableButton1(true)
            view?.enableButton2(true)
            view?.setPausePlayState(PausePlayState.PAUSE)
            startCountdown(lastRunningClock)
        }
        pauseOrPlay++
    }

    fun setCountdown(timeControl1: Int, timeControl2: Int, inc1: Int, inc2: Int) {
        setCountdownDisposer = logic.setTimeControls(timeControl1, timeControl2, inc1, inc2).subscribe({
            pauseClock1()
            pauseClock2()
            lastRunningClock = RunningClock.NONE
            view?.restartButtons(timeControl1.secondsToHMS(), timeControl2.secondsToHMS())
            view?.enableSetButton(true)
            view?.enableHomeButton(true)
            view?.setPausePlayState(PausePlayState.IDLE)
        }, {
            it.printStackTrace()
        })
    }

    fun subscribe(view: ClockView) {
        this.view = view
    }

    fun unSubscribe() {
        view = null
        setCountdownDisposer?.dispose()
        clockDisposers.forEach { (_, it) ->
            it?.dispose()
        }
    }
}