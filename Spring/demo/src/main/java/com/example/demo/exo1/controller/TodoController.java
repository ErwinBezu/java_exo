package com.example.demo.exo1.controller;

import com.example.demo.exo1.model.Todo;
import com.example.demo.exo1.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    @ResponseBody
    public Todo getFirstTodo() {
        return todoService.getFirstTodo();
    }

    @GetMapping("/todo/{id}")
    @ResponseBody
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping("/todos")
    @ResponseBody
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }
}
