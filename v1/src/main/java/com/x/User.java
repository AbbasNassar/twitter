package com.x;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class User {
    @ColumnName("id")
    private int id;
    @ColumnName("name")
    private String name;
    @ColumnName("email")
    private String email;
    @ColumnName("password")
    private String password;
    @ColumnName("date_of_birth") 
    private LocalDate date_of_birth;
    @ColumnName("created_at") 
    private LocalDateTime created_at;
    @ColumnName("updated_at") 
    private LocalDateTime updated_at; 

    public User(){

    }

    public User(int id, String name, String email, String password, LocalDate dateOfBirth, LocalDateTime createdAt,LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password =password;
        this.date_of_birth = dateOfBirth;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }

    
    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String encPass = PasswordUtils.encryptPassword(password);
        this.password = encPass;
    }

    public LocalDate getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.date_of_birth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.created_at = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    // toString Method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + date_of_birth +
                ", createdAt=" + created_at +
                ", updatedAt=" + updated_at +
                '}';
    }
}

