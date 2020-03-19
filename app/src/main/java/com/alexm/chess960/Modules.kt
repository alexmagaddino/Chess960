package com.alexm.chess960

import com.alexm.chess960.clockpack.Clock
import com.alexm.chess960.clockpack.mvp.ClockLogic
import com.alexm.chess960.clockpack.mvp.ClockPresenter
import com.alexm.chess960.randompos.mvp.RandomLogic
import com.alexm.chess960.randompos.mvp.RandomPresenter
import org.koin.dsl.module


/**
 * Created by alexm on 18/03/2020
 */
val randomModule = module {
    single { RandomLogic() }
    single { RandomPresenter(get()) }
}

val clockModule = module {
    factory { (color: ChessColor, timer: Long, increment: Int) -> Clock(color, timer, increment) }
    single { ClockLogic() }
    single { ClockPresenter(get()) }
}