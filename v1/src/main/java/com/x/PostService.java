package com.x;

import java.util.List;

import com.google.inject.Inject;

public class PostService {
    private static PostDAO postDAO; 
    
    @Inject
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }
    public void addPost(Post post) {
        postDAO.insertPost(post);
    }
    public static List<Post> getUserPosts(int Id){
        return postDAO.getUserPosts(Id);
    }
    public Post getPost(int id){
        return postDAO.getPost(id);
    }
        
}
