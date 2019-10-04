package com.alexm.chess960.randompos

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chess960.chess960.R

class RandomActivity : AppCompatActivity(), IRandomView {

    private var textA: TextView? = null
    private var textB: TextView? = null
    private var textC: TextView? = null
    private var textD: TextView? = null
    private var textE: TextView? = null
    private var textF: TextView? = null
    private var textG: TextView? = null
    private var textH: TextView? = null
    private var btnShow: Button? = null
    private var presenter: RandomPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_random)

        presenter = RandomPresenter()

        btnShow = findViewById(R.id.ShowButton)
        textA = findViewById(R.id.textA)
        textB = findViewById(R.id.textB)
        textC = findViewById(R.id.textC)
        textD = findViewById(R.id.textD)
        textE = findViewById(R.id.textE)
        textF = findViewById(R.id.textF)
        textG = findViewById(R.id.textG)
        textH = findViewById(R.id.textH)

        btnShow?.setOnClickListener {
            presenter?.generateRandomPos()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unSubscribe()
    }

    override fun showRandomPos(pos: RandomPos) {
        textA!!.text = pos.getPezzo(0)
        textB!!.text = pos.getPezzo(1)
        textC!!.text = pos.getPezzo(2)
        textD!!.text = pos.getPezzo(3)
        textE!!.text = pos.getPezzo(4)
        textF!!.text = pos.getPezzo(5)
        textG!!.text = pos.getPezzo(6)
        textH!!.text = pos.getPezzo(7)
    }
}