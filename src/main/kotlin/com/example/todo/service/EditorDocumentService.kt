package com.example.todo.service

import com.example.todo.domain.EditorDocument
import com.example.todo.repository.EditorDocumentRepository
import org.springframework.stereotype.Service

@Service
class EditorDocumentService(
    private val repository: EditorDocumentRepository
) {

    fun findAll(): List<EditorDocument> = repository.findAll()

    fun findById(id: Long): EditorDocument =
        repository.findById(id).orElseThrow { NoSuchElementException("문서를 찾을 수 없습니다. id=$id") }

    fun save(title: String, contentHtml: String): EditorDocument =
        repository.save(EditorDocument(title = title, contentHtml = contentHtml))

    fun delete(id: Long) = repository.deleteById(id)
}