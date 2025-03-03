package com.x;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
@RegisterBeanMapper(User.class)
public interface UserDAO {

    @SqlQuery("SELECT username FROM users")
    List<String> getUsersUsername();

    @SqlQuery("SELECT username FROM users WHERE id = :id")
    String getUserUsername(@Bind ("id") int id);

    @SqlUpdate("INSERT INTO users (name, email, password, date_of_birth, created_at, updated_at, username) VALUES (:user.name, :user.email, :user.password, :user.dateOfBirth, :user.createdAt, :user.updatedAt, :user.username)")
    void insertUser(@BindBean("user") User user);

    @RegisterBeanMapper(User.class)
    @SqlQuery("SELECT id, name, email, password, date_of_birth, created_at, updated_at, username FROM users WHERE email = :email")
    User getUser(@Bind ("email") String email);

    @RegisterBeanMapper(User.class)
    @SqlQuery("SELECT id, name, email, password, date_of_birth, created_at, updated_at, username FROM users WHERE name like :name")
    List <User> getSearchedUsers(@Bind ("name") String name);

    @SqlQuery("SELECT name FROM users WHERE id = :id")
    String getUserName(@Bind ("id") int id);

    @SqlQuery("SELECT id FROM users WHERE email = :email")
    int getUserId(@Bind("email") String email); 

    @SqlQuery("SELECT password FROM users WHERE email = :email")
    String checkUserPassword(@Bind("email") String email);

    @SqlUpdate("UPDATE users SET name = :name, email = :email, password = :password, dateOfBirth = :dateOfBirth ,updated_on = :updatedOn, username = :username WHERE id = :id")
    void updateUser(@BindBean("user") User user);

    @SqlQuery("SELECT email FROM users")             
    List<String> listAllEmails();

    @SqlQuery("SELECT * FROM users ORDER BY id DESC")
    List<User> listAll();

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void deleteUser(String id);
}
