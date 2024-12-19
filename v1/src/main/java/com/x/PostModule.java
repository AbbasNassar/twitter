package com.x;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
// N-Tier Architecture Implementation -> Infrastructure Layer
public class PostModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind Jdbi instance

        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/twitter", "root", "123123")
        .installPlugin(new SqlObjectPlugin()); 

        bind(Jdbi.class).toInstance(jdbi);

        // Bind DAO
        bind(PostService.class);
        bind(PostController.class);
    }
    @Provides
    @Singleton
    public PostDAO PostDao(Jdbi jdbi){
        return jdbi.onDemand(PostDAO.class);
    }
}
