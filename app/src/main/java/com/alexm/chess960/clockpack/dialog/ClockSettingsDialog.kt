package com.alexm.chess960.clockpack.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.alexm.chess960.clockpack.ClockActivity
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
                    close()
                }
                button.setOnClickListener {
                    val timeControl = set_time_control.text.toString().toLong()
                    val timeIncrement = set_time_inc.text.toString().toLong()
                    presenter.setClock(timeControl, timeIncrement)
                }
            }

    private fun close() {
        fragment_container.visibility = GONE
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onSuccess() {
        (activity as ClockActivity).setFromDialog()
        close()
    }

    override fun onFail() {
        Toast.makeText(activity, R.string.error, LENGTH_SHORT).show()
    }
}