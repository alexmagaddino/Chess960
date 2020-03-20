package com.alexm.chess960.clockpack.dialog

import io.reactivex.rxjava3.disposables.Disposable

/**
 * created by alexm on 19/03/2020
 * */
class ClockSettingPresenter(private val logic: ClockSettingLogic) {

    private var view: ClockSettingsView? = null
    private var disposable: Disposable? = null

    fun setClock(timeControl: Long, timeIncrement: Long) {
        disposable = logic.setClock(timeControl, timeIncrement).subscribe({
            view?.onSuccess()
        }, {
            it.printStackTrace()
            view?.onFail()
        })
    }

    fun subscribe(view: ClockSettingsView) {
        this.view = view
    }

    fun unsubscribe() {
        this.view = null
        this.disposable?.dispose()
    }
}