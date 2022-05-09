package io.github.chameleontartu.apiratelimitspring.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
internal class ApiControllerTest {

    val restTemplate = RestTemplate()

    companion object {
        const val BASE_URL = "http://localhost:8080"
        const val API_VERSION_URL = "api/version"
        const val API_VERSION_RESPONSE = "0.1.0"

        const val PING_URL = "ping"
        const val PING_RESPONSE = "pong"

        const val OK_STATUS_CODE = 200
        const val TOO_MANY_REQUESTS_STATUS_CODE = 429

        const val UNBOUND_URL = "unbound"
        const val UNBOUND_RESPONSE = "unbound"
    }

    @Test
    fun `should return too many requests on 101st request`() {
        var apiVersion: ResponseEntity<String>
        for (i in 1..100) {
            apiVersion = restTemplate.getForEntity("$BASE_URL/$API_VERSION_URL", String::class.java)
            assertEquals(API_VERSION_RESPONSE, apiVersion.body)
            assertEquals(OK_STATUS_CODE, apiVersion.statusCode.value())
        }

        val throwable = assertThrows(HttpClientErrorException::class.java) {
            restTemplate.getForEntity("$BASE_URL/$API_VERSION_URL", String::class.java)
        }
        assertEquals("$TOO_MANY_REQUESTS_STATUS_CODE : \"Too Many Requests\"", throwable.message)
    }

    @Test
    fun `should recover after period time`() {
        var pingResponse: ResponseEntity<String> = restTemplate.getForEntity("$BASE_URL/$PING_URL", String::class.java)
        assertEquals(PING_RESPONSE, pingResponse.body)
        assertEquals(OK_STATUS_CODE, pingResponse.statusCode.value())

        assertThrows(HttpClientErrorException::class.java) {
            restTemplate.getForEntity("$BASE_URL/$PING_URL", String::class.java)
        }

        // Sleep for 10 seconds
        Thread.sleep(10 * 1000)

        pingResponse = restTemplate.getForEntity("$BASE_URL/$PING_URL", String::class.java)
        assertEquals(PING_RESPONSE, pingResponse.body)
        assertEquals(OK_STATUS_CODE, pingResponse.statusCode.value())
    }

    @Test
    fun `should show that endpoint is unbound`() {
        var unboundResponse: ResponseEntity<String>
        for (i in 1 .. 1000) {
            unboundResponse = restTemplate.getForEntity("$BASE_URL/$UNBOUND_URL", String::class.java)
            assertEquals(UNBOUND_RESPONSE, unboundResponse.body)
            assertEquals(OK_STATUS_CODE, unboundResponse.statusCode.value())
        }
    }
}
