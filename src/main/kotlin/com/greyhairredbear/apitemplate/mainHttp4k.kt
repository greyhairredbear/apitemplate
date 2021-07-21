package com.greyhairredbear.apitemplate

import com.greyhairredbear.apitemplate.dal.setupDb
import com.greyhairredbear.apitemplate.http4k.server
import kotlinx.coroutines.runBlocking

private const val SERVER_PORT = 7070

/*
fun main() {
    IO.fx {
        !effect { setupDb() }
        !effect {  }
    }.unsafeRunAsync {
        // TODO
    }
}
*/

fun main(): Unit = runBlocking {
    setupDb()
    server(SERVER_PORT).start()
}
