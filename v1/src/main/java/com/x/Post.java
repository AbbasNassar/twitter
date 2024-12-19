package com.x;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private int userId;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(int id, int userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
