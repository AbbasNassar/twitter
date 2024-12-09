package com.x;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.google.inject.Inject;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController{

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/", this::renderIndex);          // Render main page
        app.get("/users", this::renderUsersList); // Returns partial HTML for user list
        app.post("/users", this::addUser);        // Handles user addition
        app.delete("/users/{id}", this::deleteUser); // Handles user deletion
    }

    private void renderIndex(Context ctx) {
        // Render the main page template using Pebble
        ctx.render("templates/index.peb");
    }

    private void renderUsersList(Context ctx) {
        // Return partial HTML snippet with current users
        ctx.contentType("text/html");
        ctx.result(renderUsersHTML(userService.getAllUsers()));
    }

   private void addUser(Context ctx) {
    String name = ctx.formParam("name");
    String email = ctx.formParam("email");
    String password = ctx.formParam("password");
    String dobString = ctx.formParam("dateOfBirth"); // Expected format: yyyy-MM-dd
    LocalDate dateOfBirth = LocalDate.parse(dobString);

    // Set timestamps for created_at and updated_at
    LocalDateTime now = LocalDateTime.now();

    User user = new User(name, email, password, dateOfBirth, now, now);

    // Assume userService.addUser has been updated to accept these parameters
    userService.addUser(user);

    ctx.contentType("text/html");
    ctx.result(renderUsersHTML(userService.getAllUsers()));
}


    private void deleteUser(Context ctx) {
        String id = ctx.pathParam("id");
        userService.deleteUser(id);
        ctx.contentType("text/html");
        ctx.result(renderUsersHTML(userService.getAllUsers()));
    }

    private String renderUsersHTML(List<User> users) {
        // Return a snippet of HTML to be inserted by HTMX
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append("<tr>")
              .append("<td>").append(u.getId()).append("</td>")
              .append("<td>").append(u.getName()).append("</td>")
              .append("<td>").append(u.getEmail()).append("</td>")
              .append("<td>")
              .append("<button class='btn btn-danger' hx-delete='/users/")
              .append(u.getId())
              .append("' hx-target='#users-table-body' hx-swap='innerHTML'>Delete</button>")
              .append("</td>")
              .append("</tr>");
        }
        return sb.toString();
    }
}
