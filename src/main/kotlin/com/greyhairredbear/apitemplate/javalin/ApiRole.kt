package com.greyhairredbear.apitemplate.javalin

import io.javalin.core.security.Role

enum class ApiRole: Role {
    ANYONE,
    AUTHENTICATED_USER
}
