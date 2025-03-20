package com.x;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ReactionDAO {
    @RegisterBeanMapper(Reaction.class)
    @SqlQuery("SELECT * FROM reactions where post_id = :post_id")
    Reaction getPostReactions(@Bind ("post_id") int post_id);

    @SqlUpdate("INSERT INTO reactions (post_id, likes, retweets, comments, views) VALUES (:post_id, 0, 0, 0, 0)")
    void insertReaction(@Bind("post_id") int post_id);

    @SqlUpdate("UPDATE reactions SET likes = likes + :delta WHERE post_id = :post_id")
    void updateLikes(@Bind("post_id") int postId, @Bind("delta") int delta);

    @SqlUpdate("UPDATE reactions SET retweets = retweets + :delta WHERE post_id = :post_id")
    void updateRetweets(@Bind("post_id") int postId, @Bind("delta") int delta);

    @SqlUpdate("UPDATE reactions SET comments = comments + :delta WHERE post_id = :post_id")
    void updateComments(@Bind("post_id") int postId, @Bind("delta") int delta);

    @SqlUpdate("UPDATE reactions SET views = views + :delta WHERE post_id = :post_id")
    void updateViews(@Bind("post_id") int postId, @Bind("delta") int delta);


}
