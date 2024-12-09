package com.x;

import java.util.List;

import com.google.inject.Inject;

// N-Tier Architecture Implementation -> Data Access Layer
public class ToDoRepository {
    private ToDoDAO todoDAO;
    @Inject
    public ToDoRepository(ToDoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    public List<ToDo> getAllTodos() {
        return todoDAO.getAllTodos();
    }

    public void addTodo(ToDo todo) {
        todoDAO.insertTodo(todo);
    }

    public void updateTodo(ToDo todo) {
        todoDAO.updateTodo(todo);
    }

    public void deleteTodo(String id) {
        todoDAO.deleteTodo(id);
    }
}
