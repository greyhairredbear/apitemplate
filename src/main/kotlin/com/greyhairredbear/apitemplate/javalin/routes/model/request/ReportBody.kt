package com.greyhairredbear.apitemplate.javalin.routes.model.request

import java.time.LocalDateTime

data class ReportBody(
    val scans: List<Scan>
)

data class Scan(
    val tag: String,
    val timeOfScan: LocalDateTime,
    val endTime: LocalDateTime?
)
