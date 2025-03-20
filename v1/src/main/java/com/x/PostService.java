package com.x;

import java.util.List;

import com.google.inject.Inject;

public class PostService {
    private static PostDAO postDAO; 
    private static ReactionDAO reactionDAO;
    
    @Inject
    public PostService(PostDAO postDAO, ReactionDAO reactionDAO) {
        this.postDAO = postDAO;
        this.reactionDAO = reactionDAO;
    }

    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }
    public int addPost(Post post) {
        int postId = postDAO.insertPost(post);
        reactionDAO.insertReaction(postId); 
        return postId;
    }
    public static List<Post> getUserPosts(int Id){
        return postDAO.getUserPosts(Id);
    }
    public Post getPost(int id){
        return postDAO.getPost(id);
    }
        
}
