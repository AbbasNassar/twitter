package com.x;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
// N-Tier Architecture Implementation -> Infrastructure Layer
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind Jdbi instance

        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/todo_db", "root", "123123")
        .installPlugin(new SqlObjectPlugin()); 

        bind(Jdbi.class).toInstance(jdbi);

        // Bind DAO
        bind(UserService.class);
        bind(UserController.class);
    }
    @Provides
    @Singleton
    public UserDAO todoDao(Jdbi jdbi){
        return jdbi.onDemand(UserDAO.class);
    }
}
