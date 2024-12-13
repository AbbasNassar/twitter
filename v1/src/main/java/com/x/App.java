package com.x;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinPebble;



public class App {
    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/twitter";
        String user = "root";
        String password = "123123";

        FlywayMigration.migrateDatabase(url, user, password);

        // Create Guice injector
        Injector injector = Guice.createInjector(new UserModule());

        // Get TodoController from Guice
        UserController controller = injector.getInstance(UserController.class);
        PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();

        Javalin app = Javalin.create(config -> {
        config.fileRenderer(new JavalinPebble(engine));
        config.staticFiles.add("static");
    });
        app.start(9090);
        controller.registerRoutes(app);

    }
}
