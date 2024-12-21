package com.x;

import java.time.LocalDateTime;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Post {
    @ColumnName("id")
    private int id;
    @ColumnName("user_id")
    private int userId;
    @ColumnName("content")
    private String content;
    @ColumnName("created_at")
    private LocalDateTime createdAt;
    @ColumnName("updated_at")
    private LocalDateTime updatedAt;

    public Post(){

    }

    public Post(int id, int userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
