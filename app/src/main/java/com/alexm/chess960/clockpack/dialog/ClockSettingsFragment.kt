package com.alexm.chess960.clockpack.dialog

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.alexm.chess960.clockpack.ClockActivity
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.clock_settings_dialog.view.*
import org.koin.android.ext.android.inject


/**
 * Created by alexm on 06/10/2019
 */
class ClockSettingsFragment : Fragment(), ClockSettingsView {

    private val presenter by inject<ClockSettingPresenter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.clock_settings_dialog, container, false).apply {
                this.close_popup.setOnClickListener {
                    close(false)
                }
                this.button.setOnClickListener {
                    val timeControl = set_time_control.text.toString().toLong()
                    val timeIncrement = set_time_inc.text.toString().toLong()
                    presenter.setClock(timeControl, timeIncrement)
                }
            }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    private fun close(set: Boolean) {
        activity?.supportFragmentManager?.popBackStack()
        val inputMethodManager = activity?.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val windowToken = activity?.currentFocus?.windowToken
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        (activity as ClockActivity).setFromDialog(set)
    }

    override fun onSuccess() {
        close(true)
    }

    override fun onFail() {
        Toast.makeText(activity, R.string.error, LENGTH_SHORT).show()
    }
}