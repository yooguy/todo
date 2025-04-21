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
        Thread.sleep(1000) // ✅ 1초간 지연 (htmx 로딩 테스트용)
        return ModelAndView("pages/guides/common :: content")
    }

    @GetMapping("/design-content")
    fun designContent(): ModelAndView {
        Thread.sleep(800) // ✅ 0.8초간 지연 (htmx 로딩 테스트용)
        return ModelAndView("pages/guides/design :: content")
    }

    @GetMapping("/component-content")
    fun componentContent(): ModelAndView {
        Thread.sleep(400) // ✅ 0.4초간 지연 (htmx 로딩 테스트용)
        return ModelAndView("pages/guides/component :: content")
    }
}