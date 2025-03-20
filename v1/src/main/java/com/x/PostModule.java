package com.x;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class PostModule extends AbstractModule {
    @Override
    protected void configure() {
        // No Jdbi binding here
        bind(PostService.class);
        bind(PostController.class);
    }

    @Provides
    @Singleton
    public PostDAO postDao(Jdbi jdbi) { // Jdbi is now provided by DatabaseModule
        return jdbi.onDemand(PostDAO.class);
    }
}