package com.greyhairredbear.apitemplate.javalin.routes

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.greyhairredbear.apitemplate.javalin.ApiRole
import com.greyhairredbear.apitemplate.javalin.routes.model.request.ReportBody
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.core.security.SecurityUtil
import io.javalin.plugin.openapi.dsl.OpenApiDocumentation
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import org.apache.http.HttpStatus

fun reportDocumentation(): OpenApiDocumentation = document()
    .body<ReportBody>()
    .result<Unit>(HttpStatus.SC_NO_CONTENT.toString())

// TODO make all routes suspend funs, then pass as effect...
fun report() = post("/report",
    documented(reportDocumentation()) { ctx ->
        IO.fx {
            !effect {
                // TODO
                "helloreport"
            }
        }.unsafeRunAsync {
            it.fold(
                { ctx.status(HttpStatus.SC_INTERNAL_SERVER_ERROR) },
                { ctx.status(HttpStatus.SC_NO_CONTENT) }
            )
        }
    },
    SecurityUtil.roles(ApiRole.ANYONE)
)
