package com.alexm.chess960

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alexm.chess960.clockpack.ClockActivity
import com.alexm.chess960.randompos.RandomActivity
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        goto_random.setOnClickListener(onClick)
        goto_rules.setOnClickListener(onClick)
        goto_clock.setOnClickListener(onClick)
    }

    private val onClick = fun(v: View) {
        when (v) {
            goto_random -> Intent(this, RandomActivity::class.java)
            goto_rules -> Intent(this, RulesActivity::class.java)
            goto_clock -> Intent(this, ClockActivity::class.java)
            else -> null
        }?.let(::startActivity)
    }
}