package com.greyhairredbear.apitemplate.http4k

import com.greyhairredbear.apitemplate.javalin.routes.model.response.VersionResponse
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun server(port: Int): Http4kServer = app().asServer(Jetty(port))

fun app(): HttpHandler = ServerFilters.CatchLensFailure.then(
    routes(
        "/version" bind GET to { _: Request -> Response(OK).body(VersionResponse(version = "0.1.0").version) },  // TODO understand this...
        "/add" bind GET to { request: Request ->
            val valuesToAdd = Query.int().multi.defaulted("value", listOf())(request)
            Response(OK).body(valuesToAdd.sum().toString())
        }
    )
)
