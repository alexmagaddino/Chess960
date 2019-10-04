package com.alexm.chess960

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alexm.chess960.clockpack.ClockActivity
import com.alexm.chess960.randompos.RandomActivity
import com.example.chess960.chess960.R

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val openRandom = Intent(this@MainMenu, RandomActivity::class.java)
        val openRules = Intent(this@MainMenu, RulesActivity::class.java)
        val openClock = Intent(this@MainMenu, ClockActivity::class.java)
        val gotoRandom = findViewById<View>(R.id.GotoRandom) as Button
        val gotoRules = findViewById<View>(R.id.GotoRules) as Button
        val gotoClock = findViewById<View>(R.id.GotoClock) as Button
        gotoRandom.setOnClickListener { startActivity(openRandom) }
        gotoRules.setOnClickListener { startActivity(openRules) }
        gotoClock.setOnClickListener { startActivity(openClock) }
    }
}
