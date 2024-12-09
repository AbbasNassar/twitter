package com.x;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/todo_db";
        String user = "root";
        String password = "123123";

        FlywayMigration.migrateDatabase(url, user, password);

        // Create Guice injector
        Injector injector = Guice.createInjector(new ToDoModule());

        // Get TodoController from Guice
        ToDoController controller = injector.getInstance(ToDoController.class);

        // Start Javalin app
        Javalin app = Javalin.create().start(7000);
        controller.registerRoutes(app);
    }
}
