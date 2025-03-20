package com.x;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        // No need to bind Jdbi here if using @Provides
    }

    @Provides
    @Singleton
    public Jdbi provideJdbi() {
        return Jdbi.create("jdbc:mysql://localhost:3306/twitter", "root", "123123")
                .installPlugin(new SqlObjectPlugin());
    }
}