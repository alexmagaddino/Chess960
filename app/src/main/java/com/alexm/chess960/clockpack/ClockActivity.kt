package com.alexm.chess960.clockpack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.alexm.chess960.PausePlayState
import com.alexm.chess960.RunningClock
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.android.synthetic.main.clock_menu_bar.*
import org.koin.android.ext.android.inject

class ClockActivity : AppCompatActivity(), ClockView {

    private val presenter by inject<ClockPresenter>()

    //I have to change the static init with the use of the shared Prefs
    private var timeControl1 = 300
    private var timeControl2 = 300
    private var timeInc1 = 0
    private var timeInc2 = 0

    private var clockFragment: ClockSettingsDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        btnClock1.setOnClickListener(onClick)
        btnClock2.setOnClickListener(onClick)
        btnHome.setOnClickListener(onClick)
        btnPausePlay.setOnClickListener(onClick)
        btnRestart.setOnClickListener(onClick)
        btnSettings.setOnClickListener(onClick)
    }

    private val onClick = fun(v: View?) {
        when (v) {
            btnClock1 -> presenter.startCountdown(RunningClock.CLOCK_2)
            btnClock2 -> presenter.startCountdown(RunningClock.CLOCK_1)

            btnPausePlay -> presenter.pausePlay()
            btnRestart -> presenter.setCountdown(timeControl1, timeControl2, timeInc1, timeInc2)

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
        presenter.setCountdown(timeControl1, timeControl2, timeInc1, timeInc2)
    }

    override fun onStop() {
        super.onStop()
        presenter.unSubscribe()
    }

    override fun showCountdown(remaningTime: String, clockSel: RunningClock) {
        when (clockSel) {
            RunningClock.CLOCK_1 -> btnClock1!!.text = remaningTime
            RunningClock.CLOCK_2 -> btnClock2!!.text = remaningTime
            else -> throw Exception("It was called showCountdown with the NONE value of RunningClock enum set")
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
            PausePlayState.IDLE -> btnPausePlay?.setText(R.string.pause_play)
            PausePlayState.PLAY -> btnPausePlay?.setText(R.string.play)
            PausePlayState.PAUSE -> btnPausePlay?.setText(R.string.pause)
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
        if (clockFragment == null) {
            clockFragment = ClockSettingsDialog().also {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, it, ClockSettingsDialog.TAG)
                        .commit()
            }

            container.visibility = VISIBLE
        }

//        val settingsDialog = Dialog(this)
//        settingsDialog.setTitle("Imposta Orologi")
//        settingsDialog.setContentView(R.layout.clock_settings)
//        val edtTime1 = settingsDialog.findViewById<EditText>(R.id.SetClock1)
//        val edtTime2 = settingsDialog.findViewById<EditText>(R.id.SetClock2)
//        val edtInc1 = settingsDialog.findViewById<EditText>(R.id.SetInc1)
//        val edtInc2 = settingsDialog.findViewById<EditText>(R.id.SetInc2)
//        settingsDialog.findViewById<View>(R.id.btnSet).setOnClickListener {
//            if (edtTime1.text.toString().isEmpty())
//                timeControl1 = Integer.valueOf(edtTime1.text.toString()) * 60
//            if (edtTime2.text.toString().isEmpty())
//                timeControl2 = Integer.valueOf(edtTime2.text.toString()) * 60
//
//            presenter!!.setCountdown(timeControl1, timeControl2,
//                    if (edtInc1.text.toString().isEmpty()) Integer.valueOf(edtInc1.text.toString()) else 0,
//                    if (edtInc2.text.toString().isEmpty()) Integer.valueOf(edtInc2.text.toString()) else 0)
//
//            enableRestartButton(true)
//            enableHomeButton(true)
//            settingsDialog.dismiss()
//        }
//        settingsDialog.show()
    }
}