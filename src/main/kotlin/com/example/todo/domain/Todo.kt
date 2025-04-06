package com.example.todo.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "todos")
class Todo(
    var title: String,
    var completed: Boolean = false,
    var startDate: LocalDate? = null,  // ✅ 시작일
    var endDate: LocalDate? = null     // ✅ 종료일
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
