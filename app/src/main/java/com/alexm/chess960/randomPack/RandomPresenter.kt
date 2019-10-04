package com.alexm.chess960.randomPack

import com.alexm.chess960.interfaces.RandomContract

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by alexm on 21/05/2018.
 */

class RandomPresenter : RandomContract.IPresenter {

    private var view: RandomContract.IView? = null
    private val logic: RandomContract.ILogic
    private var disposer: Disposable? = null

    init {
        logic = RandomLogic()
    }

    override fun subscribe(view: RandomContract.IView) {
        this.view = view
    }

    override fun unSubscribe() {
        view = null
        disposer?.dispose()
    }

    override fun generateRandomPos() {
        logic.generateRandomPos().subscribe(object : Observer<RandomPos> {

            override fun onSubscribe(d: Disposable) {
                disposer = d
            }

            override fun onNext(randomPos: RandomPos) {
                view!!.showRandomPos(randomPos)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {

            }
        })
    }
}