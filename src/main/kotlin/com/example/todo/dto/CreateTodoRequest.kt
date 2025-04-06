package com.example.todo.dto

import java.time.LocalDate

data class CreateTodoRequest(
    val title: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?
)