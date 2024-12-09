package com.x;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.google.inject.Inject;

// N-Tier Architecture Implementation -> Business Logic Layer
public class ToDoService {
    private ToDoRepository repository;
    @Inject
    public ToDoService(ToDoRepository repository) {
        this.repository = repository;
    }

    public List<ToDo> getAllTodos() {
        return repository.getAllTodos();
    }

    public void addTodo(ToDo todo) {
        todo.setId(UUID.randomUUID().toString());
        todo.setCreatedOn(ZonedDateTime.now());
        repository.addTodo(todo);
    }

    public void updateTodo(String id, ToDo todo) {
        todo.setId(id);
        todo.setUpdatedOn(ZonedDateTime.now());
        repository.updateTodo(todo);
    }

    public void deleteTodo(String id) {
        repository.deleteTodo(id);
    }
}
