package com.example.todo.configuration

import jakarta.annotation.PostConstruct
import java.io.File
import org.springframework.stereotype.Component

@Component
class LogDirectoryInitializer {

    @PostConstruct
    fun createLogDir() {
        val logDir = File(System.getProperty("user.dir") + "/logs")
        if (!logDir.exists()) {
            logDir.mkdirs()
            println("log directory created: ${logDir.absolutePath}")
        }
    }
}