package com.greyhairredbear.apitemplate

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.greyhairredbear.apitemplate.dal.setupDb

private const val SERVER_PORT = 7070

fun main() {
    IO.fx {
        !effect { setupDb() }
        !effect {  }
    }.unsafeRunAsync {
        // TODO
    }
}
