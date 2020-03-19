package com.alexm.chess960.clockpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.chess960.chess960.R


/**
 * Created by alexm on 06/10/2019
 */
class ClockSettingsDialog : Fragment() {

    companion object {
        val TAG = ClockSettingsDialog::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return View.inflate(activity, R.layout.clock_settings_dialog, container)
    }

    override fun onStart() {
        super.onStart()
        view?.findViewById<Button>(R.id.button)?.setOnClickListener {

        }
    }
}