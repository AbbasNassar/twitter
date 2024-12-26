package com.x;

import java.util.List;

import com.google.inject.Inject;

public class PostService {
    private final PostDAO postDAO; 
    
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
    public List<Post> getUserPost(int Id){
        return postDAO.getUserPosts(Id);
    }
    public Post getPost(int id){
        return postDAO.getPost(id);
    }
        
}
