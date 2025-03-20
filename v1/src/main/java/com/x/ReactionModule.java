package com.x;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ReactionModule extends AbstractModule{
    @Override
    protected void configure() {
        // No Jdbi binding here
        bind(ReactionService.class);
    }

    @Provides
    @Singleton
    public ReactionDAO reactionDao(Jdbi jdbi) { // Jdbi is now provided by DatabaseModule
        return jdbi.onDemand(ReactionDAO.class);
    }
}
