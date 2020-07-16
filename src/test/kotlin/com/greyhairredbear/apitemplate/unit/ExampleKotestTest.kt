package com.greyhairredbear.apitemplate.unit

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldNotBeLessThan
import kotlin.random.Random

class ExampleKotestTest : BehaviorSpec({
    Given("Two numbers greater 0") {
        val random = Random(4321)
        val a = random.nextInt(0, Int.MAX_VALUE)
        val b = random.nextInt(0, Int.MAX_VALUE)

        When("Adding them") {
            val result = a + b
            Then("The result should be greater than 0") {
                result shouldBeGreaterThanOrEqual  0
            }

            Then("The result is not less than 0") {
                result shouldNotBeLessThan 0
            }
        }
    }
})
