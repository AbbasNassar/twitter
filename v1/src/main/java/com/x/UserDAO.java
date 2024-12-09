package com.x;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
// N-Tier Architecture Implementation -> Data Access Layer
public interface UserDAO {
    @SqlQuery("SELECT * FROM users")
    @RegisterBeanMapper(User.class)
    List<User> getAllTodos();

    @SqlUpdate("INSERT INTO users (id, name, email, password, dateOfBirth, createdAt, updatedAt) VALUES (:id, :name, :email, :password, :dateOfBirth, :createdAt, :updatedAt)")
    void insertUser(@BindBean User user);

    @SqlUpdate("UPDATE users SET name = :name, email = :email, password = :password, dateOfBirth = :dateOfBirth ,updated_on = :updatedOn WHERE id = :id")
    void updateUser(@BindBean User user);

    @SqlQuery("SELECT * FROM users ORDER BY id DESC")
    List<User> listAll();

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void deleteUser(String id);
}
