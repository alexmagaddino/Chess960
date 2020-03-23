package com.alexm.chess960

import com.alexm.chess960.clockpack.ClockActivity
import com.alexm.chess960.clockpack.ClockPreferences
import com.alexm.chess960.clockpack.dialog.ClockSettingLogic
import com.alexm.chess960.clockpack.dialog.ClockSettingPresenter
import com.alexm.chess960.clockpack.dialog.ClockSettingsFragment
import com.alexm.chess960.clockpack.mvp.ClockLogic
import com.alexm.chess960.clockpack.mvp.ClockPresenter
import com.alexm.chess960.clockpack.vo.Clock
import com.alexm.chess960.randompos.RandomFragment
import com.alexm.chess960.randompos.mvp.RandomLogic
import com.alexm.chess960.randompos.mvp.RandomPresenter
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module


/**
 * Created by alexm on 18/03/2020
 */
val randomModule = module {
    scope<RandomFragment> {
        scoped { RandomLogic() }
        scoped { RandomPresenter(get()) }
    }
}

val clockModule = module {
    factory { (color: ChessColor, timer: Long, increment: Int) -> Clock(color, timer, increment) }
    scope<ClockActivity> {
        scoped { ClockLogic() }
        scoped { ClockPresenter(get()) }
    }
}

val clockDialogModule = module {
    single { ClockPreferences(get()) }
    scope<ClockSettingsFragment> {
        scoped { ClockSettingLogic(get()) }
        scoped { ClockSettingPresenter(get()) }
    }
    fragment { ClockSettingsFragment() }
}