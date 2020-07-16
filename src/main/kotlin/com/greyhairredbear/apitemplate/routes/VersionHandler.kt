package com.greyhairredbear.apitemplate.routes

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.greyhairredbear.apitemplate.dal.Version
import com.greyhairredbear.apitemplate.dal.max
import com.greyhairredbear.apitemplate.javalin.ApiRole
import com.greyhairredbear.apitemplate.routes.model.response.VersionResponse
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.core.security.SecurityUtil
import io.javalin.plugin.openapi.dsl.OpenApiDocumentation
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import org.apache.http.HttpStatus
import org.jetbrains.exposed.sql.transactions.transaction

fun versionDocumentation(): OpenApiDocumentation = document()
    .result<VersionResponse>(HttpStatus.SC_OK.toString())

// TODO make all routes suspend funs, then pass as effect...
fun version(): Unit = get(
    "/version",
    documented(versionDocumentation()) { ctx ->
        IO.fx {
            !effect { transaction { Version.all().max() } }
        }.unsafeRunAsync {
            it.fold(
                { ctx.status(HttpStatus.SC_INTERNAL_SERVER_ERROR) },
                { ctx.result(it.toString()) }
            )
        }
    },
    SecurityUtil.roles(ApiRole.ANYONE)
)
