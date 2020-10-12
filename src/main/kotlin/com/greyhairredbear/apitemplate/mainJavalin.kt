package com.greyhairredbear.apitemplate

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.greyhairredbear.apitemplate.dal.setupDb
import com.greyhairredbear.apitemplate.javalin.app

// TODO: extract to config...
private const val SERVER_PORT = 7070

fun main() {
    IO.fx {
        !effect { setupDb() }
        !effect { app().start(SERVER_PORT) }
    }.unsafeRunAsync {
        // TODO
    }
}
