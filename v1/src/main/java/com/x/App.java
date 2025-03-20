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

        String url = "jdbc:mysql://localhost:3306/twitter?allowMultiQueries=true";
        String user = "root";
        String password = "123123";

        FlywayMigration.migrateDatabase(url, user, password);

        PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();
        Injector injector = Guice.createInjector(new DatabaseModule(),new UserModule(), new PostModule(), new ReactionModule());
        UserController controller = injector.getInstance(UserController.class);
        PostController postController = injector.getInstance(PostController.class);

        Javalin app = Javalin.create(config -> {
        config.fileRenderer(new JavalinPebble(engine));
        config.staticFiles.add("/static");
        
    });
        app.start(9090);
        controller.registerRoutes(app);
        postController.registerRoutes(app);

    }
}
