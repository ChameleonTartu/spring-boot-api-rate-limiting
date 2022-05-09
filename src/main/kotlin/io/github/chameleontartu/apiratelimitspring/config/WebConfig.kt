package io.github.chameleontartu.apiratelimitspring.config

import io.github.chameleontartu.apiratelimitspring.interceptors.CustomRestAnnotationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    @Autowired
    lateinit var customRestAnnotationInterceptor: CustomRestAnnotationInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customRestAnnotationInterceptor).addPathPatterns("/**")
    }
}
