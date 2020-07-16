import Versions.ARROW_VERSION
import Versions.EXPOSED_VERSION
import Versions.H2_VERSION
import Versions.JAVALIN_VERSION
import Versions.KOTEST_VERSION
import Versions.MOCKK_VERSION
import Versions.REST_ASSURED_VERSION
import Versions.SL4J_VERSION

object Versions {
    const val KOTLIN_VERSION = "1.3.72"

    const val ARROW_VERSION = "0.10.5"

    const val JAVALIN_VERSION = "3.9.1"
    const val SL4J_VERSION = "1.7.30"

    const val EXPOSED_VERSION = "0.26.1"
    //    const val POSTGRES_VERSION = "42.2.14"
    const val H2_VERSION = "1.4.200"

    const val KOTEST_VERSION = "4.1.0"
    const val MOCKK_VERSION = "1.10.0"
    const val REST_ASSURED_VERSION = "4.3.0"

    const val DETEKT_VERSION = "1.10.0-RC1"
}

object Plugins {
    const val KOTLIN = "org.jetbrains.kotlin.jvm"
    const val KAPT = "kapt"
    const val DETEKT = "io.gitlab.arturbosch.detekt"
}

object Core {
    private const val ARROW_GROUP = "io.arrow-kt"
    const val STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val ARROW_FX = "$ARROW_GROUP:arrow-fx:$ARROW_VERSION"
    const val ARROW_OPTICS = "$ARROW_GROUP:arrow-optics:$ARROW_VERSION"
    const val ARROW_SYNTAX = "$ARROW_GROUP:arrow-syntax:$ARROW_VERSION"
    const val ARROW_META = "$ARROW_GROUP:arrow-meta:$ARROW_VERSION"
}

object ServerFramework {
    private const val JAVALIN_GROUP = "io.javalin"
    const val JAVALIN = "$JAVALIN_GROUP:javalin:$JAVALIN_VERSION"
    const val JAVALIN_OPENAPI = "$JAVALIN_GROUP:javalin-openapi:$JAVALIN_VERSION"

    const val SL4J = "org.slf4j:slf4j-simple:$SL4J_VERSION"
}

object Data {
    private const val EXPOSED_GROUP = "org.jetbrains.exposed"
    const val EXPOSED_CORE = "$EXPOSED_GROUP:exposed-core:$EXPOSED_VERSION"
    const val EXPOSED_JDBC = "$EXPOSED_GROUP:exposed-jdbc:$EXPOSED_VERSION"
    const val EXPOSED_JAVA_TIME = "$EXPOSED_GROUP:exposed-java-time:$EXPOSED_VERSION"
    const val EXPOSED_DAO = "$EXPOSED_GROUP:exposed-dao:$EXPOSED_VERSION"

//    const val POSTGRES_JDBC = "org.postgresql:postgresql:$POSTGRES_VERSION"
}

object Testing {
    private const val KOTEST_GROUP = "io.kotest"
    const val KOTEST_RUNNER = "$KOTEST_GROUP:kotest-runner-junit5-jvm:$KOTEST_VERSION"
    const val KOTEST_RUNNER_CONSOLE = "$KOTEST_GROUP:kotest-runner-console-jvm:$KOTEST_VERSION"
    const val KOTEST_ASSERTIONS = "$KOTEST_GROUP:kotest-assertions-core-jvm:$KOTEST_VERSION"
    const val KOTEST_PROPERTIES = "$KOTEST_GROUP:kotest-property-jvm:$KOTEST_VERSION"

    private const val REST_ASSURED_GROUP = "io.rest-assured"
    const val REST_ASSURED = "$REST_ASSURED_GROUP:rest-assured:$REST_ASSURED_VERSION"
    const val REST_ASSURED_KTX = "$REST_ASSURED_GROUP:kotlin-extensions:$REST_ASSURED_VERSION"

    const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"

    const val H2_TEST_DB = "com.h2database:h2:$H2_VERSION"
}
