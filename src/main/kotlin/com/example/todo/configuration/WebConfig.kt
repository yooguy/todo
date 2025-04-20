package com.example.todo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
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


    private val templatePaths: MutableSet<String> = mutableSetOf()

    override fun addViewControllers(registry: ViewControllerRegistry) {
        // 홈 페이지 수동 등록
        registry.addViewController("/").setViewName("pages/home")
        templatePaths.add("/")

        // 나머지 HTML 경로 자동 등록
        val resources = PathMatchingResourcePatternResolver()
            .getResources("classpath:/templates/pages/**/*.html")

        resources.mapNotNull { resource ->
            runCatching {
                val path = resource.url.path
                val viewName = path.substringAfter("/templates/").substringBefore(".html")
                val urlPath = "/$viewName"
                templatePaths.add(urlPath)
                urlPath to viewName
            }.getOrNull()
        }.forEach { (urlPath, viewName) ->
            registry.addViewController(urlPath).setViewName(viewName)
            registry.addViewController("${urlPath}-content").setViewName("${viewName} :: content")
        }
    }


    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/vendors/**")
            .addResourceLocations("classpath:/static/vendors/")
            .setCachePeriod(31556926) // 1년
            .resourceChain(true)
            .addResolver(VersionResourceResolver().addContentVersionStrategy("/**"))
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(RequestInjectInterceptor(templatePaths))
    }

    @Bean
    fun resourceUrlEncodingFilter(): ResourceUrlEncodingFilter {
        return ResourceUrlEncodingFilter()
    }
}
