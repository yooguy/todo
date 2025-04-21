package com.example.todo.controller.api

import com.example.todo.domain.EditorDocument
import com.example.todo.service.EditorDocumentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/docs")
class EditorDocumentController(
    private val service: EditorDocumentService
) {

    @GetMapping
    fun list(): List<EditorDocument> = service.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): EditorDocument = service.findById(id)

    @PostMapping
    fun create(@RequestBody request: EditorDocumentRequest): EditorDocument =
        service.save(request.title, request.contentHtml)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)
}

data class EditorDocumentRequest(
    val title: String,
    val contentHtml: String
)