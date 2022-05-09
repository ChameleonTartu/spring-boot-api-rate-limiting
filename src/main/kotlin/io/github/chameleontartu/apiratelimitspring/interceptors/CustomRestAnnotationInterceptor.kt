package io.github.chameleontartu.apiratelimitspring.interceptors

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import io.github.chameleontartu.apiratelimitspring.annotations.ApiRateLimit
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomRestAnnotationInterceptor : HandlerInterceptor {
    private val requestBucket: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            var hasApiRateLimit: ApiRateLimit? = handler.getMethodAnnotation(ApiRateLimit::class.java)
            if (hasApiRateLimit == null) {
                hasApiRateLimit = handler.method.declaringClass
                    .getAnnotation(ApiRateLimit::class.java)
            }

            if (hasApiRateLimit != null && requestBucket[request.requestURI] == null) {
                val limit: Long = hasApiRateLimit.limit
                val refill = Refill.intervally(limit, Duration.ofSeconds(10))
                val bandwidth = Bandwidth.classic(limit, refill)
                requestBucket[request.requestURI] = Bucket.builder().addLimit(bandwidth).build()
            }

            val rateLimitHit = !requestBucket[request.requestURI]!!.tryConsume(1)
            if (rateLimitHit) {
                response.writer.write("Too Many Requests")
                response.status = 429
                return false
            }
        }
        return true
    }
}
