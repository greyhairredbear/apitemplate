package com.greyhairredbear.apitemplate.integration.http4k

import com.greyhairredbear.apitemplate.dal.setupDb
import com.greyhairredbear.apitemplate.http4k.server
import io.kotest.core.spec.style.FunSpec
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo

class VersionIntegrationTest: FunSpec({
    test("Version API should return 200 as status code and correct version as body") {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = 7070

        server(7070).start()

        setupDb()

        When {
            get("/version")
        } Then {
            statusCode(200)
            body(equalTo("0.1.0"))
        }
    }
})
