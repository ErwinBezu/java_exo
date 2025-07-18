package com.example.demo.service;

import com.example.demo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> todos;
    private Long nextId = 1L;

    public TodoService() {
        todos = new ArrayList<>();
        todos.add(new Todo(nextId++, "Faire les courses", "Acheter du pain, lait et fruits", false));
        todos.add(new Todo(nextId++, "Réviser Spring", "Étudier les concepts de base de Spring Boot", true));
        todos.add(new Todo(nextId++, "Exercice Java", "Terminer l'exercice sur les TODOs", false));
        todos.add(new Todo(nextId++, "Rendez-vous médecin", "Consultation de routine à 14h", false));
    }

    public List<Todo> getAllTodos() {
        return todos;
    }

    public Todo getFirstTodo() {
        if (todos.isEmpty()) {
            return null;
        }
        return todos.get(0);
    }

    public Todo getTodoById(Long id) {
        if (id == null) return null;
        return todos.stream()
                .filter(todo -> id.equals(todo.getId()))
                .findFirst()
                .orElse(null);
    }
}
