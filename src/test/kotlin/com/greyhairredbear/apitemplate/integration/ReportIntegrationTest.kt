package com.greyhairredbear.apitemplate.integration

import com.greyhairredbear.apitemplate.dal.setupDb
import com.greyhairredbear.apitemplate.javalin.app
import io.kotest.core.spec.style.FunSpec
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.equalTo

class ReportIntegrationTest: FunSpec({
    test("Report route should return 204 as status code and correct body") {
        RestAssured.baseURI = "http://localhost"

        // TODO: maybe use different ports per test to ensure no port conflict when running integration tests in parallel
        RestAssured.port = 7070

        // TODO extract setup / shutdown to common integration test
        setupDb()
        val app = app().start(7070)

        When {
            post("/report")
        } Then {
            statusCode(HttpStatus.SC_NO_CONTENT)
            body(equalTo(""))
        }

        app.stop()
    }
})
