package com.alexm.chess960.clockpack.mvp

import android.content.Context
import com.alexm.chess960.ChessColor
import com.alexm.chess960.ChessColor.BLACK
import com.alexm.chess960.ChessColor.WHITE
import com.alexm.chess960.PausePlayState
import com.alexm.chess960.PausePlayState.PAUSE
import com.alexm.chess960.PausePlayState.PLAY
import com.alexm.chess960.clockpack.vo.Clock
import com.alexm.chess960.secondsToHMS
import com.example.chess960.chess960.R
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by alexm on 21/05/2018.
 */
internal class ClockPresenter(private val logic: ClockLogic) : KoinComponent {

    private var view: ClockView? = null
    private val context by inject<Context>()
    private lateinit var clockWhite: Clock
    private lateinit var clockBlack: Clock

    private var settingsDisposable: Disposable? = null
    private val clockDisposers =
            mutableMapOf<ChessColor, Disposable?>()

    private var pauseOrPlay = PausePlayState.IDLE
    private var lastRunningClock: ChessColor? = null

    private fun getSelectedClock(color: ChessColor) = if (color.isWhite()) clockWhite else clockBlack

    private fun alternateEnabledButton(color: ChessColor) {
        color.isWhite().also {
            view?.enableButton1(it)
            view?.enableButton2(!it)
        }
    }

    fun startCountdown(clockSel: ChessColor) {
        logic.tick(getSelectedClock(clockSel)).subscribe(object : Observer<String> {
            override fun onComplete() {
                view?.showCountdown(context.getString(R.string.time_expired), clockSel)
                view?.finishCountdown()
            }

            override fun onSubscribe(d: Disposable?) {
                pauseClock(!clockSel)
                logic.addIncrement(getSelectedClock(!clockSel)).subscribe()
                pauseOrPlay = PLAY
                lastRunningClock = clockSel
                alternateEnabledButton(clockSel)
                view?.enableHomeButton(false)
                view?.enableRestartButton(false)
                view?.enableSetButton(false)
                view?.setPausePlayState(PAUSE)
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

    fun pauseClock(color: ChessColor) {
        clockDisposers[color]?.dispose()
    }

    /**
     * this method control the pauseOrPlay field:
     * if is an even number pause clocks, blocks the buttons and save who was the last running clock.
     * else enable the buttons and control who was the last running clock (0 none, 1 the first, 2 the second)
     * at the end increase the pauseOrPlay to change state
     */
    fun pausePlay() {
        view?.setPausePlayState(pauseOrPlay)
        when (pauseOrPlay) {
            PLAY -> {
                pauseClock(WHITE)
                pauseClock(BLACK)
                view?.enableButton1(false)
                view?.enableButton2(false)
                view?.enableHomeButton(true)
                view?.enableSetButton(true)
                view?.enableRestartButton(true)
                pauseOrPlay = PAUSE
            }
            PAUSE -> {
                view?.enableButton1(true)
                view?.enableButton2(true)
                lastRunningClock?.apply(::startCountdown)
                pauseOrPlay = PLAY
            }
            else -> {
                //nothing
            }
        }
    }

    fun setCountdown() {
        settingsDisposable = logic.getStoredSettings(get()).subscribe({ (timeControl, inc) ->
            pauseClock(WHITE)
            pauseClock(BLACK)
            clockWhite = get { parametersOf(WHITE, timeControl, inc) }
            clockBlack = get { parametersOf(BLACK, timeControl, inc) }
            lastRunningClock = null
            view?.restartButtons(clockWhite.timer.secondsToHMS(), clockBlack.timer.secondsToHMS())
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
        settingsDisposable?.dispose()
        clockDisposers.forEach { (_, it) ->
            it?.dispose()
        }
    }
}