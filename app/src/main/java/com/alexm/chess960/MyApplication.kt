package com.alexm.chess960

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * Created by alexm on 18/03/2020
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(modules)
        }
    }
}