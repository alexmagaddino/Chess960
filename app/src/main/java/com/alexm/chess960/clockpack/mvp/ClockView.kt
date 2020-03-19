package com.alexm.chess960.clockpack.mvp

import com.alexm.chess960.ChessColor
import com.alexm.chess960.PausePlayState

/**
 * Created by alexm on 04/10/2019
 */
interface ClockView {
    fun showCountdown(remaningTime: String, clockSel: ChessColor)
    fun enableButton1(enabled: Boolean)
    fun enableButton2(enabled: Boolean)
    fun enableHomeButton(enabled: Boolean)
    fun enableSetButton(enabled: Boolean)
    fun enableRestartButton(enabled: Boolean)
    fun setPausePlayState(state: PausePlayState)
    fun finishCountdown()
    fun restartButtons(initText1: String, initText2: String)
}