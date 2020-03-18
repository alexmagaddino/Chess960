package com.alexm.chess960

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexm.chess960.clockpack.ClockActivity
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.fragment_main_menu.view.*

class MainMenuFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main_menu,
            container, false).also { rootView ->

        rootView.goto_random.setOnClickListener {
            findNavController().navigate(R.id.action_to_random)
        }
        rootView.goto_rules.setOnClickListener {
            findNavController().navigate(R.id.action_to_rules)
        }
        rootView.goto_clock.setOnClickListener {
            startActivity(Intent(activity, ClockActivity::class.java))
        }
    }
}