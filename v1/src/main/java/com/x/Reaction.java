package com.x;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Reaction {
    @ColumnName("post_id")
    private int postId;

    @ColumnName("likes")
    private int likes;

    @ColumnName("retweets")
    private int retweets;

    // Default constructor
    public Reaction() {

    }

    // Constructor with parameters
    public Reaction(int postId, int likes, int retweets) {
        this.postId = postId;
        this.likes = likes;
        this.retweets = retweets;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    // toString Method
    @Override
    public String toString() {
        return "Reaction{" +
                "postId=" + postId +
                ", likes=" + likes +
                ", retweets=" + retweets +
                '}';
    }
}

