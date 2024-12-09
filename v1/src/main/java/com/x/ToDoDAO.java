package com.x;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;

import java.util.List;
// N-Tier Architecture Implementation -> Data Access Layer
public interface ToDoDAO {
    @SqlQuery("SELECT * FROM todos")
    @RegisterBeanMapper(ToDo.class)
    List<ToDo> getAllTodos();

    @SqlUpdate("INSERT INTO todos (id, title, description, done, created_on) VALUES (:id, :title, :description, :done, :createdOn)")
    void insertTodo(@BindBean ToDo todo);

    @SqlUpdate("UPDATE todos SET title = :title, description = :description, done = :done, updated_on = :updatedOn WHERE id = :id")
    void updateTodo(@BindBean ToDo todo);

    @SqlUpdate("DELETE FROM todos WHERE id = :id")
    void deleteTodo(String id);
}
