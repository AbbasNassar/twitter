package com.x;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PostDAO {
    @SqlQuery("SELECT * FROM posts")
    @RegisterBeanMapper(Post.class)
    List<Post> getAllPosts();

    @SqlUpdate("INSERT INTO posts (user_id, content, created_at, updated_at) VALUES (:post.user_id, :post.content, :post.createdAt, :post.updatedAt, :user.createdAt, :user.updatedAt)")
    void insertPost(@BindBean("post") Post post);

    @SqlUpdate("DELETE FROM posts WHERE id = :id")
    void deletePost(String id);
}
