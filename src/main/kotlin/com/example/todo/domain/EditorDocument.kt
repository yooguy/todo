package com.example.todo.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class EditorDocument(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    @Lob
    @Column(columnDefinition = "TEXT")
    val contentHtml: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)