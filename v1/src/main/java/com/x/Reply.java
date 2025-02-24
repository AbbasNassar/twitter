package com.x;

import java.time.LocalDateTime;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Reply {
    @ColumnName("id")
    private int id;
    @ColumnName("comment_id")
    private int commentId;
    @ColumnName("user_id")
    private int userId;
    @ColumnName("replieValue")
    private String replieValue;
    @ColumnName("created_at")
    private LocalDateTime createdAt;
    @ColumnName("updated_at")
    private LocalDateTime updatedAt;

    public Reply() {
    }

    public Reply(int id, int commentId, int userId, String replieValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.replieValue = replieValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReplieValue() {
        return replieValue;
    }

    public void setReplieValue(String replieValue) {
        this.replieValue = replieValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", userId=" + userId +
                ", replieValue='" + replieValue + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}