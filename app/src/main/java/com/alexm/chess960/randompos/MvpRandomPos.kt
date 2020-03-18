package com.alexm.chess960.randompos

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * Created by alexm on 04/10/2019
 */
interface RandomView {
    fun showRandomPos(pos: RandomPos)
}

class RandomPresenter(private val logic: RandomLogic) {

    private var view: RandomView? = null
    private var disposer: Disposable? = null

    fun subscribe(view: RandomView) {
        this.view = view
    }

    fun unSubscribe() {
        view = null
        disposer?.dispose()
    }

    fun generateRandomPos() {
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

class RandomLogic {
    fun generateRandomPos(): Observable<RandomPos> {
        return Observable.defer {
            val pos = RandomPos()
            pos.shuffle()
            Observable.just(pos)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}