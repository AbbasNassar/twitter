package com.x;

import com.google.inject.Inject;

import io.javalin.Javalin;
// N-Tier Architecture Implementation -> Presentation Layer
public class ToDoController {
    private ToDoService service;


    @Inject
    public ToDoController(ToDoService service) {
        this.service = service;
    }

    public void registerRoutes(Javalin app) {
        app.get("", ctx -> {
            String welcome = "Welcome to my ToDo application";
            ctx.json(welcome);
        });
        
        app.get("/todo", ctx -> ctx.json(service.getAllTodos()));
        System.out.println(service.getAllTodos());

        app.post("/todo", ctx -> {
            ToDo todo = ctx.bodyAsClass(ToDo.class);
            service.addTodo(todo);
            ctx.status(201).json(todo);
        });

        app.put("/todo/{id}", ctx -> {
            String id = ctx.pathParam("id");
            ToDo todo = ctx.bodyAsClass(ToDo.class);
            service.updateTodo(id, todo);
            ctx.status(204);
        });

        app.delete("/todo/{id}", ctx -> {
            String id = ctx.pathParam("id");
            service.deleteTodo(id);
            ctx.status(204);
        });
    }
}
