package io.github.chameleontartu.apiratelimitspring.annotations

/**
 * Annotation sets limit for number of requests per 10 seconds.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ApiRateLimit(val limit: Long)
