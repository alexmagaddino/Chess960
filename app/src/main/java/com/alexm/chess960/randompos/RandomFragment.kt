package com.alexm.chess960.randompos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexm.chess960.randompos.mvp.RandomPresenter
import com.alexm.chess960.randompos.mvp.RandomView
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.fragment_random.view.*
import org.koin.android.ext.android.inject

class RandomFragment : Fragment(), RandomView {

    private lateinit var rootView: View
    private val presenter by inject<RandomPresenter>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_random, container, false)

        rootView.btn_show.setOnClickListener {
            presenter.generateRandomPos()
        }
        return rootView
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unSubscribe()
    }

    override fun showRandomPos(pos: RandomPos) {
        rootView.textA.text = pos.getPezzo(0)
        rootView.textB.text = pos.getPezzo(1)
        rootView.textC.text = pos.getPezzo(2)
        rootView.textD.text = pos.getPezzo(3)
        rootView.textE.text = pos.getPezzo(4)
        rootView.textF.text = pos.getPezzo(5)
        rootView.textG.text = pos.getPezzo(6)
        rootView.textH.text = pos.getPezzo(7)
    }
}