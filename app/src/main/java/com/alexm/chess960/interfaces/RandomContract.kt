package com.alexm.chess960.interfaces

import com.alexm.chess960.randomPack.RandomPos

import io.reactivex.Observable

/**
 * Created by alexm on 21/05/2018.
 */

interface RandomContract {
    interface IView {
        fun showRandomPos(pos: RandomPos)
    }

    interface IPresenter {
        fun subscribe(view: IView)
        fun unSubscribe()
        fun generateRandomPos()
    }

    interface ILogic {
        fun generateRandomPos(): Observable<RandomPos>
    }
}
