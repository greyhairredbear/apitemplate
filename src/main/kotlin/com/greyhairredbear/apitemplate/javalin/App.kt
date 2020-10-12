package com.greyhairredbear.apitemplate.javalin

import com.greyhairredbear.apitemplate.javalin.routes.report
import com.greyhairredbear.apitemplate.javalin.routes.version
import io.javalin.Javalin
import io.javalin.core.security.BasicAuthCredentials
import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info

// TODO: DI (openapiOptions)
fun app(): Javalin {
    return Javalin.create { config ->
        config.registerPlugin(OpenApiPlugin(getOpenApiOptions()))
        config.accessManager { handler, context, permittedRoles ->
            when {
                permittedRoles.contains(ApiRole.ANYONE) ||
                    context.userRoles.contains(ApiRole.AUTHENTICATED_USER) -> handler.handle(context)
                else -> throw UnauthorizedResponse()
            }
        }
    }.routes {
        version()
        report()
    }
}

private fun getOpenApiOptions(): OpenApiOptions {
    val applicationInfo: Info = Info()
        .version("1.0")
        .description("My Application")
    return OpenApiOptions(applicationInfo)
        .path("/swagger-docs")
        .roles(setOf(ApiRole.ANYONE))
        .swagger(SwaggerOptions("/swagger").title("API template"))
}

private val Context.userRoles
    // TODO: getting basic auth credentials throws anytime they're not present -> handle this case
    get() = this.basicAuthCredentials().let { credentials ->
        userRoleMap[credentials] ?: setOf()
    }

// TODO replace with realistic user role handling
private val userRoleMap = hashMapOf(
    BasicAuthCredentials("alice", "weak-password") to setOf(ApiRole.ANYONE),
    BasicAuthCredentials("bob", "better-password") to setOf(ApiRole.AUTHENTICATED_USER)
)
