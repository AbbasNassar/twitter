package com.x;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PostDAO {
    @RegisterBeanMapper(Post.class)
    @SqlQuery("SELECT * FROM posts")
    List<Post> getAllPosts();

    @SqlUpdate("INSERT INTO posts (user_id, content, created_at, updated_at, retweet_id) " +
    "VALUES (:post.userId, :post.content, :post.createdAt, :post.updatedAt, :post.retweetId)")
    @GetGeneratedKeys("id") // Maps the auto-generated ID to the `id` field of the `Post` object
    int insertPost(@BindBean("post") Post post);

    @RegisterBeanMapper(Post.class)
    @SqlQuery("SELECT id ,user_id, content, created_at, updated_at, retweet_id FROM posts where user_id=:userId")
    List<Post> getUserPosts(@Bind ("userId") int userId);

    @RegisterBeanMapper(Post.class)
    @SqlQuery("SELECT id ,user_id, content, created_at, updated_at, retweet_id FROM posts where id=:postId")
    Post getPost(@Bind ("postId") int postId);

}
