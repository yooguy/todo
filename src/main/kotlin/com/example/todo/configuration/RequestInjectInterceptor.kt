package com.example.todo.configuration

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class RequestInjectInterceptor (
    private val templatePaths: Set<String>
) : HandlerInterceptor {

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val path = request.servletPath
        if (templatePaths.contains(path)) {
            modelAndView?.addObject("request", request)
            modelAndView?.addObject("response", response)
        }
    }
}