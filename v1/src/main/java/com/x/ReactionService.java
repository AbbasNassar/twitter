package com.x;

import com.google.inject.Inject;

public class ReactionService {
    private static ReactionDAO reactionDAO;

    @Inject
    public ReactionService(ReactionDAO reactionDAO) {
        this.reactionDAO = reactionDAO;
    }

    public Reaction getPostReaction(int post_id) {
        return reactionDAO.getPostReactions(post_id);
    }
    public void insertReaction(int post_id){
        reactionDAO.insertReaction(post_id);
    }
    public void updateLikes(int post_id, int delta) {
        reactionDAO.updateLikes(post_id, delta);
    }
    public void updateRetweets(int post_id, int delta){
        reactionDAO.updateRetweets(post_id, delta);
    }
    public void updateComments(int post_id, int delta){
        reactionDAO.updateComments(post_id, delta);
    }
    public void updateViews(int post_id, int delta){
        reactionDAO.updateViews(post_id, delta);
    }

}
