package com.alexm.chess960.randompos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexm.chess960.randompos.mvp.RandomPresenter
import com.alexm.chess960.randompos.mvp.RandomView
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.actitivity_random.*
import org.koin.android.ext.android.inject

class RandomActivity : AppCompatActivity(), RandomView {

    private val presenter by inject<RandomPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitivity_random)

        btn_show.setOnClickListener {
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
        textA.text = pos.getPezzo(0)
        textB.text = pos.getPezzo(1)
        textC.text = pos.getPezzo(2)
        textD.text = pos.getPezzo(3)
        textE.text = pos.getPezzo(4)
        textF.text = pos.getPezzo(5)
        textG.text = pos.getPezzo(6)
        textH.text = pos.getPezzo(7)
    }
}