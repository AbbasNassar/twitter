package com.x;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

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
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/index.peb");
        Writer writer = new StringWriter();

        Map<String, Object> context = new HashMap<>();
        context.put("websiteTitle", "My First Website");
        context.put("content", "My Interesting Content");

        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        Javalin app = Javalin.create(config -> {
        config.fileRenderer(new JavalinPebble(engine));
        config.staticFiles.add("static");
    });
        app.start(9090);
        controller.registerRoutes(app);

    }
}
