package com.example.todo.controller.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/pages/guides")
class ViewController {

    @GetMapping("/common-content")
    fun commonContent(): ModelAndView {
        Thread.sleep(2000) // ✅ 3초간 지연 (htmx 로딩 테스트용)
        return ModelAndView("pages/guides/common :: content")
    }

    @GetMapping("/component-content")
    fun componentContent(): ModelAndView {
        Thread.sleep(2000) // ✅ 3초간 지연 (htmx 로딩 테스트용)
        return ModelAndView("pages/guides/component :: content")
    }
}