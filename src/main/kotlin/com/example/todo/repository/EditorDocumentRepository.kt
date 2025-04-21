package com.example.todo.repository

import com.example.todo.domain.EditorDocument
import org.springframework.data.jpa.repository.JpaRepository

interface EditorDocumentRepository : JpaRepository<EditorDocument, Long>