package com.alexm.chess960.clockpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.alexm.chess960.ChessColor
import com.alexm.chess960.ChessColor.BLACK
import com.alexm.chess960.ChessColor.WHITE
import com.alexm.chess960.PausePlayState
import com.alexm.chess960.clockpack.dialog.ClockSettingsFragment
import com.alexm.chess960.clockpack.mvp.ClockPresenter
import com.alexm.chess960.clockpack.mvp.ClockView
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.activity_clock.*
import org.koin.android.ext.android.inject

class ClockActivity : AppCompatActivity(), ClockView {

    private val presenter by inject<ClockPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        btnClock1.setOnClickListener(onClick)
        btnClock2.setOnClickListener(onClick)
        btnHome.setOnClickListener(onClick)
        btnPausePlay.setOnClickListener(onClick)
        btnRestart.setOnClickListener(onClick)
        btnSettings.setOnClickListener(onClick)
        presenter.setCountdown()
    }

    private val onClick = fun(v: View?) {
        when (v) {
            btnClock1 -> presenter.startCountdown(BLACK)
            btnClock2 -> presenter.startCountdown(WHITE)

            btnPausePlay -> presenter.pausePlay()
            btnRestart -> presenter.setCountdown()

            btnHome -> finish()
            btnSettings -> createSettingsDialog()
        }
    }

    override fun onBackPressed() {
        //nothing
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unSubscribe()
    }

    fun setFromDialog(set: Boolean) {
        fragment_container.visibility = View.GONE
        if (set) presenter.setCountdown()
    }

    override fun showCountdown(remaningTime: String, clockSel: ChessColor) {
        when (clockSel) {
            WHITE -> btnClock1.text = remaningTime
            BLACK -> btnClock2.text = remaningTime
        }
    }

    override fun enableButton1(enabled: Boolean) {
        btnClock1.isEnabled = enabled
    }

    override fun enableButton2(enabled: Boolean) {
        btnClock2.isEnabled = enabled
    }

    override fun enableHomeButton(enabled: Boolean) {
        btnHome.isEnabled = enabled
    }

    override fun enableSetButton(enabled: Boolean) {
        btnSettings.isEnabled = enabled
    }

    override fun enableRestartButton(enabled: Boolean) {
        btnRestart.isEnabled = enabled
    }

    @SuppressLint("SetTextI18n")
    override fun setPausePlayState(state: PausePlayState) {
        when (state) {
            PausePlayState.IDLE -> btnPausePlay.setText(R.string.pause_play)
            PausePlayState.PLAY -> btnPausePlay.setText(R.string.play)
            PausePlayState.PAUSE -> btnPausePlay.setText(R.string.pause)
        }
    }

    override fun finishCountdown() {
        enableButton1(false)
        enableButton2(false)
    }

    override fun restartButtons(initText1: String, initText2: String) {
        enableButton1(true)
        enableButton2(true)
        btnClock1.text = initText1
        btnClock2.text = initText2
    }

    private fun createSettingsDialog() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                        ClockSettingsFragment::class.java, null, null)
                .commit()

        fragment_container.visibility = VISIBLE
    }
}