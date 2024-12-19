package com.x;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String email;
    private String password; 
    private LocalDate dateOfBirth; 
    private final LocalDateTime createdAt; 
    private LocalDateTime updatedAt; 

    
    public User(String name, String email, String password, LocalDate dateOfBirth, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.password = PasswordUtils.encryptPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    
    public int getId() {
        return id;
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
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString Method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

