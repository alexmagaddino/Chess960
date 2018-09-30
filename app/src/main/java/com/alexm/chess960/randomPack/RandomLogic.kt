package com.alexm.chess960.randomPack

import com.alexm.chess960.interfaces.RandomContract
import io.reactivex.Observable
import io.reactivex.Observable.defer
import io.reactivex.Observable.just
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by alexm on 21/05/2018.
 */

class RandomLogic : RandomContract.ILogic {

    override fun generateRandomPos(): Observable<RandomPos> {
        return defer {
            val pos = RandomPos()
            pos.shuffle()
            just(pos)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}