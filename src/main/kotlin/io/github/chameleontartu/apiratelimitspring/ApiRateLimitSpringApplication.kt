package io.github.chameleontartu.apiratelimitspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiRateLimitSpringApplication

fun main(args: Array<String>) {
    runApplication<ApiRateLimitSpringApplication>(*args)
}
