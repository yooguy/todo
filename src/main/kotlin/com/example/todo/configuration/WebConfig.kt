package com.example.todo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
import org.springframework.web.servlet.resource.VersionResourceResolver

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")   // ✅ FE 서버
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
    }

    @Value("classpath:/templates/pages/**/*.html")
    private lateinit var templates: Array<Resource>

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("pages/home")

        templates.mapNotNull { resource ->
            runCatching {
                val path = resource.url.path
                val viewName = path.substringAfter("/templates/").substringBefore(".html")
                "/$viewName" to viewName
            }.getOrNull()
        }.forEach { (urlPath, viewName) ->
            registry.addViewController(urlPath).setViewName(viewName)
        }
    }


    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/vendors/**")
            .addResourceLocations("classpath:/static/vendors/")
            .setCachePeriod(31556926) // 1년
            .resourceChain(true)
            .addResolver(VersionResourceResolver().addContentVersionStrategy("/**"))
    }

    @Bean
    fun resourceUrlEncodingFilter(): ResourceUrlEncodingFilter {
        return ResourceUrlEncodingFilter()
    }
}
