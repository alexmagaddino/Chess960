package com.alexm.chess960.randompos

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by alexm on 04/10/2019
 */
interface IRandomView {
    fun showRandomPos(pos: RandomPos)
}

interface IRandomPresenter {
    fun subscribe(view: IRandomView)
    fun unSubscribe()
    fun generateRandomPos()
}

interface IRandomLogic {
    fun generateRandomPos(): Observable<RandomPos>
}

class RandomPresenter : IRandomPresenter {

    private var view: IRandomView? = null
    private val logic: IRandomLogic
    private var disposer: Disposable? = null

    init {
        logic = RandomLogic()
    }

    override fun subscribe(view: IRandomView) {
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

class RandomLogic : IRandomLogic {

    override fun generateRandomPos(): Observable<RandomPos> {
        return Observable.defer {
            val pos = RandomPos()
            pos.shuffle()
            Observable.just(pos)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}