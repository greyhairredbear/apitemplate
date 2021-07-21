package com.greyhairredbear.apitemplate.javalin.routes

import com.greyhairredbear.apitemplate.javalin.ApiRole
import com.greyhairredbear.apitemplate.javalin.routes.model.response.VersionResponse
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.core.security.SecurityUtil
import io.javalin.plugin.openapi.dsl.OpenApiDocumentation
import io.javalin.plugin.openapi.dsl.document
import io.javalin.plugin.openapi.dsl.documented
import org.apache.http.HttpStatus

fun versionDocumentation(): OpenApiDocumentation = document()
    .result<VersionResponse>(HttpStatus.SC_OK.toString())

// TODO make all routes suspend funs, then pass as effect...
fun version(): Unit = get(
    "/version",
    documented(versionDocumentation()) { ctx ->
//        IO.fx {
//            !effect { transaction { Version.all().max() } }
//        }.unsafeRunAsync {
//            it.fold(
//                { ctx.status(HttpStatus.SC_INTERNAL_SERVER_ERROR) },
//                { ctx.result(it.toString()) }
//            )
//        }
    },
    SecurityUtil.roles(ApiRole.ANYONE)
)
