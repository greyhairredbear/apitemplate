package com.greyhairredbear.apitemplate.dal

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

suspend fun setupDb() {
    val db = Database.connect("jdbc:h2:mem:~/test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Versions)

        val version = Version.new {
            major = 0
            minor = 1
            patch = 0
        }
    }
}
