package com.alexm.chess960.clockPack

import com.alexm.chess960.PausePlayState
import com.alexm.chess960.RunningClock
import com.alexm.chess960.interfaces.ClockContract
import com.alexm.chess960.secondsToHMS
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by alexm on 21/05/2018.
 */

internal class ClockPresenter : ClockContract.IPresenter {

    companion object {
        private const val FINISH_TEXT = "Tempo scaduto"
    }

    private var view: ClockContract.IView? = null
    private val logic: ClockLogic
    private var disposer1: Disposable? = null
    private var disposer2: Disposable? = null
    //this field need to know if is in pause or in play and who was the last clock that runnning before the pause
    private var pauseOrPlay: Int = 0
    private var lastRunningClock: RunningClock = RunningClock.NONE

    init {
        pauseOrPlay = 0
        lastRunningClock = RunningClock.NONE
        logic = ClockLogic()
    }

    override fun startCountdown(clockSel: RunningClock) {
        if (clockSel == RunningClock.CLOCK_1) {
            logic.tick1().subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable) {
                    disposer1 = d
                    pauseClock2()
                    logic.addIncrement2().subscribe()
                    lastRunningClock = RunningClock.CLOCK_1
                    view!!.enableButton1(true)
                    view!!.enableButton2(false)
                    view!!.enableHomeButton(false)
                    view!!.enableRestartButton(false)
                    view!!.enableSetButton(false)
                    view!!.setPausePlayState(PausePlayState.PAUSE)
                }

                override fun onNext(remaningTime: String) {
                    view!!.showCountdown(remaningTime, clockSel)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view!!.showCountdown(FINISH_TEXT, clockSel)
                    view!!.finishCountdown()
                }
            })
        } else {
            logic.tick2().subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable) {
                    disposer2 = d
                    pauseClock1()
                    logic.addIncrement1().subscribe()
                    lastRunningClock = RunningClock.CLOCK_2
                    view!!.enableButton1(false)
                    view!!.enableButton2(true)
                    view!!.enableHomeButton(false)
                    view!!.enableRestartButton(false)
                    view!!.enableSetButton(false)
                    view!!.setPausePlayState(PausePlayState.PAUSE)
                }

                override fun onNext(remaningTime: String) {
                    view!!.showCountdown(remaningTime, clockSel)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view!!.showCountdown(FINISH_TEXT, clockSel)
                    view!!.finishCountdown()
                }
            })
        }
    }

    override fun pauseClock1() {
        if (disposer1 != null) {
            disposer1!!.dispose()
        }
    }

    override fun pauseClock2() {
        if (disposer2 != null) {
            disposer2!!.dispose()
        }
    }

    /*this method control the pauseOrPlay field:
    if is an even number pause clocks, blocks the buttons and save who was the last running clock.
    else enable the buttons and control who was the last running clock (0 none, 1 the first, 2 the second)
    at the end increase the pauseOrPlay to change state
    */
    override fun pausePlay() {
        if (pauseOrPlay % 2 == 0) {
            pauseClock1()
            pauseClock2()
            view!!.enableButton1(false)
            view!!.enableButton2(false)
            view!!.enableHomeButton(true)
            view!!.enableSetButton(true)
            view!!.enableRestartButton(true)
            view!!.setPausePlayState(PausePlayState.PLAY)
        } else {
            view!!.enableButton1(true)
            view!!.enableButton2(true)
            view!!.setPausePlayState(PausePlayState.PAUSE)
            startCountdown(lastRunningClock)
        }
        pauseOrPlay++
    }

    override fun setCountdown(timeControl1: Int, timeControl2: Int, timeInc1: Int, timeInc2: Int) {
        logic.setTimeControls(timeControl1, timeControl2, timeInc1, timeInc2)
        pauseClock1()
        pauseClock2()
        lastRunningClock = RunningClock.NONE
        view!!.restartButtons(timeControl1.secondsToHMS(), timeControl2.secondsToHMS())
        view!!.enableSetButton(true)
        view!!.enableHomeButton(true)
        view!!.setPausePlayState(PausePlayState.IDLE)
    }

    override fun subscribe(view: ClockContract.IView) {
        this.view = view
    }

    override fun unSubscribe() {
        view = null
        try {
            disposer1!!.dispose()
            disposer2!!.dispose()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}