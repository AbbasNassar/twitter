package com.x;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Reaction {
    @ColumnName("post_id")
    private int postId;

    @ColumnName("likes")
    private int likes;

    @ColumnName("retweets")
    private int retweets;

    @ColumnName("comments")
    private int comments;

    @ColumnName("views")
    private int views;

    // Default constructor
    public Reaction() {

    }

    // Constructor with parameters
    public Reaction(int postId, int likes, int retweets, int comments, int views) {
        this.postId = postId;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.views = views;
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

    public int getComments (){
        return comments;
    }
    
    public void setComments(int comments){
        this.comments = comments;
    }

    public int getViews(){
        return views;
    }

    public void setViews(int views){
        this.views = views;
    }

    // toString Method
    @Override
    public String toString() {
        return "Reaction{" +
                "postId=" + postId +
                ", likes=" + likes +
                ", retweets=" + retweets +
                ", comments=" + comments +
                ", views=" + views +
                '}';
    }
}

