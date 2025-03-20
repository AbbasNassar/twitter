package com.x;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class);
        bind(UserController.class);
    }

    @Provides
    @Singleton
    public UserDAO userDao(Jdbi jdbi) { // Inject Jdbi from DatabaseModule
        return jdbi.onDemand(UserDAO.class);
    }
}