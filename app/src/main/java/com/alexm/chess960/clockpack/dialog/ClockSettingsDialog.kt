package com.alexm.chess960.clockpack.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.android.synthetic.main.clock_settings_dialog.view.*
import org.koin.android.ext.android.inject


/**
 * Created by alexm on 06/10/2019
 */
class ClockSettingsDialog : Fragment(), ClockSettingsView {

    private val presenter by inject<ClockSettingPresenter>()

    companion object {
        val TAG = ClockSettingsDialog::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.clock_settings_dialog, container, false).apply {

                close_popup.setOnClickListener {
                    fragment_container.visibility = GONE
                    activity?.supportFragmentManager?.popBackStack()
                }
                button.setOnClickListener {
                    val timeControl = set_time_control.text.toString().toLong()
                    val timeIncrement = set_time_inc.text.toString().toLong()
                    presenter.setClock(timeControl, timeIncrement)
                }
            }

    override fun onSuccess() {
        TODO("not implemented")
    }

    override fun onFail() {
        TODO("not implemented")
    }
}