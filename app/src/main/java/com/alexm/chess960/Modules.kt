package com.alexm.chess960

import com.alexm.chess960.clockpack.ClockLogic
import com.alexm.chess960.clockpack.ClockPresenter
import com.alexm.chess960.randompos.RandomLogic
import com.alexm.chess960.randompos.RandomPresenter
import org.koin.dsl.module


/**
 * Created by alexm on 18/03/2020
 */
val modules = listOf(
        module {
            single { ClockLogic() }
            single { ClockPresenter(get()) }
        },
        module {
            single { RandomLogic() }
            single { RandomPresenter(get()) }
        }
)