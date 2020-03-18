package com.alexm.chess960.randompos.mvp

import com.alexm.chess960.randompos.RandomPos
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * Created by alexm on 04/10/2019
 */
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