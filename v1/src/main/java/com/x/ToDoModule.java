package com.x;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
// N-Tier Architecture Implementation -> Infrastructure Layer
public class ToDoModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind Jdbi instance

        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:3306/todo_db", "root", "123123")
        .installPlugin(new SqlObjectPlugin()); 

        bind(Jdbi.class).toInstance(jdbi);

        // Bind DAO
        // Bind Repository, Service, and Controller
        bind(ToDoRepository.class);
        bind(ToDoService.class);
        bind(ToDoController.class);
    }

    @Provides
    @Singleton
    public ToDoDAO todoDao(Jdbi jdbi){
        return jdbi.onDemand(ToDoDAO.class);
    }
}
