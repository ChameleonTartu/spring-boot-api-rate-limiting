package io.github.chameleontartu.apiratelimitspring.controller

import io.github.chameleontartu.apiratelimitspring.annotations.ApiRateLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/api/version")
    @ApiRateLimit(100)
    fun apiVersion(): String {
        return "0.1.0"
    }

    @GetMapping("/ping")
    @ApiRateLimit(1)
    fun rarePing(): String {
        return "pong"
    }
    
    @GetMapping("/unbound")
    fun unbound(): String {
        return "unbound"
    }
}
