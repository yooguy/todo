package com.example.todo.controller

import com.example.todo.domain.Todo
import com.example.todo.dto.CreateTodoRequest
import com.example.todo.service.TodoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getTodos(): List<Todo> = todoService.getAllTodos()

    @PostMapping
    fun createTodo(@RequestBody request: CreateTodoRequest): Todo {
        return todoService.createTodo(
            title = request.title,
            startDate = request.startDate,
            endDate = request.endDate
        )
    }

    @PatchMapping("/{id}/toggle")
    fun toggleTodo(@PathVariable id: Long): Todo = todoService.toggleTodo(id)

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long) = todoService.deleteTodo(id)
}
