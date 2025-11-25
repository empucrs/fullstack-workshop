package com.workshop.todo.controller;

import com.workshop.todo.model.Todo;
import com.workshop.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Todo REST Controller
 *
 * Provides RESTful endpoints for TODO operations.
 */
@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*") // Allow all origins for workshop purposes
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Get all todos or filter by completion status
     *
     * GET /api/todos
     * GET /api/todos?completed=true
     *
     * @param completed optional completion status filter
     * @return list of todos
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(
            @RequestParam(required = false) Boolean completed) {

        List<Todo> todos;
        if (completed != null) {
            todos = todoService.getTodosByStatus(completed);
        } else {
            todos = todoService.getAllTodos();
        }
        return ResponseEntity.ok(todos);
    }

    /**
     * Get a todo by ID
     *
     * GET /api/todos/{id}
     *
     * @param id the todo ID
     * @return the todo if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Search todos by title
     *
     * GET /api/todos/search?title=keyword
     *
     * @param title the search term
     * @return list of matching todos
     */
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(
            @RequestParam String title) {
        List<Todo> todos = todoService.searchTodos(title);
        return ResponseEntity.ok(todos);
    }

    /**
     * Create a new todo
     *
     * POST /api/todos
     *
     * @param todo the todo to create
     * @return the created todo
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Update an existing todo
     *
     * PUT /api/todos/{id}
     *
     * @param id the todo ID
     * @param todoDetails the updated todo details
     * @return the updated todo
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Toggle the completion status of a todo
     *
     * PATCH /api/todos/{id}/toggle
     *
     * @param id the todo ID
     * @return the updated todo
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleTodoStatus(@PathVariable Long id) {
        try {
            Todo updatedTodo = todoService.toggleTodoStatus(id);
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a todo
     *
     * DELETE /api/todos/{id}
     *
     * @param id the todo ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Todo deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete all todos
     *
     * DELETE /api/todos
     *
     * @return success message
     */
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAllTodos() {
        todoService.deleteAllTodos();
        Map<String, String> response = new HashMap<>();
        response.put("message", "All todos deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     *
     * GET /api/todos/health
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "TODO API is running");
        return ResponseEntity.ok(response);
    }
}

