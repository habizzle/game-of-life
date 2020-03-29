package de.istkorrekt.gameoflife.presentation.rest

import io.restassured.RestAssured
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import spark.Spark

internal class RestControllerShould {

    @BeforeEach
    internal fun setUp() {
        main()
        Spark.awaitInitialization()
        RestAssured.port = 4567
    }

    @Test
    internal fun `return 200 with matchfield of next round`() {
        Given {
            body("""
                {
                    "matchfield": {
                        "size": {"width": 4, "height": 4},
                        "alive": [
                            {"x": 1, "y": 2}
                        ]
                    }
                }
                """.trimIndent())
        } When {
            put("/nextRound")
        } Then {
            statusCode(200)
            contentType(JSON)
        }
    }

    @Test
    internal fun `return 404 if request does not accept application-json`() {
        Given {
            accept("text/plain")
        } When {
            put("/nextRound")
        } Then {
            statusCode(404)
        }
    }

    @Test
    internal fun `return 400 if request has empty body`() {
        Given {
            body("")
        } When {
            put("/nextRound")
        } Then {
            statusCode(400)
        }
    }
}
