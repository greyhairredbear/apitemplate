
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
    jcenter()
}

plugins {
    id(Plugins.KOTLIN) version Versions.KOTLIN_VERSION
    kotlin(Plugins.KAPT) version Versions.KOTLIN_VERSION
    id(Plugins.DETEKT) version Versions.DETEKT_VERSION
    `maven-publish`
}

apply(from = "config/git-hooks/gitHooks.gradle.kts")

detekt {
    failFast = false
    buildUponDefaultConfig = true
    config = files("$projectDir/config/detekt/detekt.yml")

    reports {
        html.enabled = true
        xml.enabled = true
        txt.enabled = true
    }
}

group "com.greyhairredbear"
version "1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(Core.STD_LIB)
    implementation(ServerFramework.JAVALIN)
    implementation(ServerFramework.JAVALIN_OPENAPI)
    implementation(ServerFramework.SL4J)
    implementation(Data.EXPOSED_CORE)
    implementation(Data.EXPOSED_JDBC)
    implementation(Data.EXPOSED_DAO)
    implementation(Data.EXPOSED_JAVA_TIME)
    implementation(Core.ARROW_FX)
    implementation(Core.ARROW_OPTICS)
    implementation(Core.ARROW_SYNTAX)
    // TODO: move H2 to test impl in case replacing it by postgres for production
    implementation(Testing.H2_TEST_DB)
//    implementation(Data.POSTGRES_JDBC)

    kapt(Core.ARROW_META)

    testImplementation(Testing.KOTEST_RUNNER)
    testImplementation(Testing.KOTEST_JUNIT_RUNNER)
    testImplementation(Testing.KOTEST_ASSERTIONS)
    testImplementation(Testing.KOTEST_PROPERTIES)
    testImplementation(Testing.REST_ASSURED)
    testImplementation(Testing.REST_ASSURED_KTX)
    testImplementation(Testing.MOCKK)
//    testImplementation(Testing.H2_TEST_DB)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/LPeteR90/apitemplate")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Detekt> {
    this.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        // set options for log level LIFECYCLE
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        // set options for log level DEBUG and INFO
        debug {
            events = setOf(
                TestLogEvent.STARTED,
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_ERROR,
                TestLogEvent.STANDARD_OUT
            )
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
            override fun afterSuite(desc: TestDescriptor, result: TestResult) {
                if (desc.parent == null) {
                    val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                    val startItem = "|  "
                    val endItem = "  |"
                    val repeatLength = startItem.length + output.length + endItem.length
                    println("\n${"-".repeat(repeatLength)}\n$startItem$output$endItem\n + ${"-".repeat(repeatLength)}")
                }
            }
        })
    }
}
