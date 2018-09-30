package com.alexm.chess960.clockPack

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.alexm.chess960.PausePlayState
import com.alexm.chess960.RunningClock

import com.alexm.chess960.interfaces.ClockContract
import com.example.chess960.chess960.R

class ClockActivity : Activity(), ClockContract.IView {

    private var btnClock1: Button? = null
    private var btnClock2: Button? = null
    private var btnHome: Button? = null
    private var btnPausePlay: Button? = null
    private var btnRestart: Button? = null
    private var btnSettings: Button? = null

    private var presenter: ClockPresenter? = null

    //I have to change the static init with the use of the shared Prefs
    private var timeControl1 = 300
    private var timeControl2 = 300
    private var timeInc1 = 0
    private var timeInc2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        btnClock1 = findViewById<View>(R.id.btnClock1) as Button
        btnClock2 = findViewById<View>(R.id.btnClock2) as Button
        btnHome = findViewById<View>(R.id.btnHome) as Button
        btnPausePlay = findViewById<View>(R.id.btnPausePlay) as Button
        btnRestart = findViewById<View>(R.id.btnRestart) as Button
        btnSettings = findViewById<View>(R.id.btnSettings) as Button

        presenter = ClockPresenter()

        btnClock1!!.setOnClickListener { presenter!!.startCountdown(RunningClock.CLOCK_2) }
        btnClock2!!.setOnClickListener { presenter!!.startCountdown(RunningClock.CLOCK_1) }
        btnHome!!.setOnClickListener { finish() }
        btnPausePlay!!.setOnClickListener { presenter!!.pausePlay() }
        btnRestart!!.setOnClickListener { presenter!!.setCountdown(timeControl1, timeControl2, timeInc1, timeInc2) }
        btnSettings!!.setOnClickListener { createSettingsDialog() }
    }

    override fun onStart() {
        super.onStart()
        presenter!!.subscribe(this)
        presenter!!.setCountdown(timeControl1, timeControl2, timeInc1, timeInc2)
    }

    override fun onStop() {
        super.onStop()
        presenter!!.unSubscribe()
    }

    override fun showCountdown(remaningTime: String, clockSel: RunningClock) {
        when(clockSel){
            RunningClock.CLOCK_1 -> btnClock1!!.text = remaningTime
            RunningClock.CLOCK_2 -> btnClock2!!.text = remaningTime
            else -> throw Exception("It was called showCountdown with the NONE value of RunningClock enum set")
        }
    }

    override fun enableButton1(enabled: Boolean) {
        btnClock1!!.isEnabled = enabled
    }

    override fun enableButton2(enabled: Boolean) {
        btnClock2!!.isEnabled = enabled
    }

    override fun enableHomeButton(enabled: Boolean) {
        btnHome!!.isEnabled = enabled
    }

    override fun enableSetButton(enabled: Boolean) {
        btnSettings!!.isEnabled = enabled
    }

    override fun enableRestartButton(enabled: Boolean) {
        btnRestart!!.isEnabled = enabled
    }

    @SuppressLint("SetTextI18n")
    override fun setPausePlayState(state: PausePlayState) {
        when (state) {
            PausePlayState.IDLE -> btnPausePlay!!.text = "Pause&Play"
            PausePlayState.PLAY -> btnPausePlay!!.text = "Play"
            PausePlayState.PAUSE -> btnPausePlay!!.text = "Pause"
        }
    }

    override fun finishCountdown() {
        enableButton1(false)
        enableButton2(false)
    }

    override fun restartButtons(initText1: String, initText2: String) {
        enableButton1(true)
        enableButton2(true)
        btnClock1!!.text = initText1
        btnClock2!!.text = initText2
    }

    override fun createSettingsDialog() {
        val settingsDialog = Dialog(this)
        settingsDialog.setTitle("Imposta Orologi")
        settingsDialog.setContentView(R.layout.clock_settings)
        val edtTime1 = settingsDialog.findViewById<EditText>(R.id.SetClock1)
        val edtTime2 = settingsDialog.findViewById<EditText>(R.id.SetClock2)
        val edtInc1 = settingsDialog.findViewById<EditText>(R.id.SetInc1)
        val edtInc2 = settingsDialog.findViewById<EditText>(R.id.SetInc2)
        settingsDialog.findViewById<View>(R.id.btnSet).setOnClickListener {
            if (edtTime1.text.toString().isEmpty())
                timeControl1 = Integer.valueOf(edtTime1.text.toString()) * 60
            if (edtTime2.text.toString().isEmpty())
                timeControl2 = Integer.valueOf(edtTime2.text.toString()) * 60

            presenter!!.setCountdown(timeControl1, timeControl2,
                    if (edtInc1.text.toString().isEmpty()) Integer.valueOf(edtInc1.text.toString()) else 0,
                    if (edtInc2.text.toString().isEmpty()) Integer.valueOf(edtInc2.text.toString()) else 0)

            enableRestartButton(true)
            enableHomeButton(true)
            settingsDialog.dismiss()
        }
        settingsDialog.show()
    }
}