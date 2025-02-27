package com.x;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class UserController{
   
    private boolean nameLogIn = false;
    private boolean usernameLogIn = false;
    private boolean emailLogIn = false;
    private boolean passwordLogIn = false;
    private boolean dateLogIn = false;


    private static UserService userService;
    PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();


    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/", this::renderIndex);          // Render main page
        app.post("/users/name", this::handleName);
        app.post("/users/username", this::handleUsername);
        app.post("/users/email", this::HandleUsersEmail); // Returns partial HTML for user list
        app.post("/users/password", this::handlePasswordStrength);
        app.post("/users/addUser", this::addUser);
        app.post("/users/logInUser", this::logInUser);
        app.get("/user/home",this::renderUserPage);
        app.post("/users/search/{userEmail}",this::searchUsers);
        app.get("/user/profile/{userEmail}", this::renderUserProfile);
        app.get("user/newsfeed/{userEmail}", this::renderNewsfeed);
    }
    private void renderNewsfeed(Context ctx) throws IOException{
        String userEmail = ctx.pathParam("userEmail");
        User user = userService.getUser(userEmail);
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/newsfeed.peb");
        HashMap<String, Object> context = new HashMap<>();
        context.put("name", user.getName());
        context.put("username", user.getName() + "Xo");
        context.put("userEmail", userEmail);
        context.put("ProfileImgSource", "/img/X_logo.jpg");
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();
        ctx.result(output);
    }
    private void renderUserProfile(Context ctx) throws IOException{
        String userEmail = ctx.pathParam("userEmail");
        int id = getUserId(userEmail);
        User user = userService.getUser(userEmail);
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/profile.peb");
        HashMap<String, Object> context = new HashMap<>();
        List <Post> posts = PostService.getUserPosts(id);   
        int numberOfPosts = numberOfPosts(posts);
        context.put("name", user.getName());
        context.put("username", user.getName() + "Xo");
        context.put("userEmail", userEmail);
        context.put("numberOfPosts", numberOfPosts);
        context.put("ProfileImgSource", "/img/X_logo.jpg");
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();
        ctx.result(output);

    }
    private void renderIndex(Context ctx) {
       ctx.render("templates/index.html");
       
    }
    private void renderUserPage(Context ctx) throws IOException{
        String email = ctx.sessionAttribute("userEmail");
        User logedInUser = userService.getUser(email); 
        int id = userService.getUserId(email);
        List <Post> posts = PostService.getUserPosts(id);   
        int numberOfPosts = numberOfPosts(posts);
        String userName = logedInUser.getName();
        String userUserName = logedInUser.getName() + "Xo";
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/home.peb");
        HashMap<String, Object> context = new HashMap<>();
        context.put("name", userName);
        context.put("username", userUserName);
        context.put("userEmail", email);
        context.put("numberOfPosts", numberOfPosts);
        context.put("ProfileImgSource", "/img/X_logo.jpg");
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();
        ctx.html(output);
    }
    private void searchUsers(Context ctx) throws IOException{
        String name = ctx.formParam("search");
        if (name != null){
            if (name.isEmpty() != true){
                StringBuilder searchPattern = new StringBuilder();
                searchPattern.append("%").append(name).append("%");
                List <User> results = userService.getSearchedUsers(searchPattern.toString());
                PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/UserSearchResponse.peb");
                Writer writer = new StringWriter();
                HashMap<String, Object> context = new HashMap<>();
                String output = "";
                for ( User u : results){
                    context.put("name", u.getName());
                    context.put("userEmail", u.getEmail());
                    context.put("username", u.getName() + "Xo");
                    context.put("ProfileImgSource", "/img/X_logo.jpg");  
                    compiledTemplate.evaluate(writer, context); 
                    output = writer.toString();
                }
                ctx.result(output);     
                
            }
        }
    }
    
    private void handleName(Context ctx) throws IOException {
        String name = ctx.formParam("name");
        System.out.println(name);
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/NameResponse.peb");
        HashMap<String, Object> context = new HashMap<>();
        if (name == null || name.isEmpty() == true){
            String invalidMessage = "Name can't be empty.";
            context.put("name", name);
            context.put("message", invalidMessage);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            nameLogIn = false;
        }
        else{
            context.put("name", name);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            nameLogIn = true;
        }
    }


    private void handleUsername(Context ctx) throws IOException {
        String username = ctx.formParam("username");
        List<String> usersUsernames = userService.getUsersUsername();
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/usernameResponse.peb");
        HashMap<String, Object> context = new HashMap<>();
        if (usersUsernames.contains(username) && username.isEmpty() != true) {
            String invalidMessage = "Username is already taken";
            context.put("username", username);
            context.put("message", invalidMessage);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            usernameLogIn = false;
        } else if ( username != null && username.isEmpty() != true) {
            String validMessage = "Valid ya wa7sh";
            context.put("username", username);
            context.put("message", validMessage);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            usernameLogIn = true;
        }
        else{
            context.put("username", username);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            usernameLogIn = false;
        }
    }

    private void HandleUsersEmail(Context ctx) throws IOException {
        String email = ctx.formParam("email");
        boolean isValidEmail = EmailHandler.isValidEmail(email);
        if (isValidEmail == true){
            boolean isEmailExists = EmailHandler.isEmailInList(email, userService.getAllEmails());
            if (isEmailExists) {
                ctx.result(EmailHandler.generateEmailExistsResponse(email));
                emailLogIn = false;
            }
            else{
                ctx.result(EmailHandler.generateValidEmailResponse(email));
                emailLogIn = true;
            }
        }
        else {
            ctx.result(EmailHandler.generateInvalidEmailResponse(email));
            emailLogIn = false;
        }
    }
    private void handlePasswordStrength(Context ctx) throws IOException {
        String password = ctx.formParam("password");

        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/PasswordResponse.peb");
        HashMap<String, Object> context = new HashMap<>();
        

        if (password == null || password.isEmpty()) {
            String invalidMessage = "Password cannot be empty.";
            context.put("password", password);
            context.put("message", invalidMessage);
            context.put("cssClass", "text-danger");
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            passwordLogIn = false;
        } else {
            String strength = PasswordStrengthHandler.evaluatePasswordStrength(password);
            String cssClass = PasswordStrengthHandler.getStrengthClass(strength);
            String message = "Password strength: ";
            context.put("password", password);
            context.put("message", message);
            context.put("cssClass", cssClass);
            context.put("strength", strength);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            ctx.html(output);
            if (strength == "Strong")
                passwordLogIn = true;
            else 
                passwordLogIn = false;    
        }
    }
    private void addUser(Context ctx) throws IOException{
        String name = ctx.formParam("name");
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String day = ctx.formParam("dd");
        String month = ctx.formParam("mm");
        String year = ctx.formParam("yyyy");
        if (year.equals("year") || month.equals("month") || day.equals("day")){
            ctx.result(generateInvalidDateResponse());
            dateLogIn = false;
        }
        else {
            dateLogIn = true;
            if (nameLogIn == true && usernameLogIn == true && emailLogIn == true && passwordLogIn == true && dateLogIn == true){
                if (day.length() == 1)
                day = "0" + day;
            if (month.length() == 1)
                month = "0" + month;    
            String birthDate = year + "-" + month + "-" + day;
            LocalDate dateOfBirth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime editedAtTime = LocalDateTime.now();
            String encryptedPassword = PasswordUtils.encryptPassword(password);
            User user = new User(0,name, email, encryptedPassword, dateOfBirth, editedAtTime,editedAtTime, username);
            try {
                userService.addUser(user);
                String response = """
                    <script>
                        // Hide the modal by setting its display style to 'none'
                        document.getElementById('upModal').style.display = 'none';
                        alert('User has been added successfully!');
                    </script>
                """;
                ctx.result(response);  
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }
    }
    private void logInUser(Context ctx) throws IOException{
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/LogIn.peb");
        HashMap <String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        

        if (email == null){
            String response = """
                <script>
                    alert('Email field is empty!');
                </script>
            """;
            ctx.result(response);
        }
        else if (password == null){
            String response = """
                <script>
                    alert('Password field is empty!');
                </script>
            """;
            ctx.result(response);
        }
        else{
            boolean isEmailExists = EmailHandler.isEmailInList(email, userService.getAllEmails());
            if (!isEmailExists){
                context.put("errorMessage", "Email is not registered :(");
                compiledTemplate.evaluate(writer, context);
                String output = writer.toString();
                ctx.result(output);
            }
            else{
                String passwordFromDatabase = userService.checkPassword(email);
                if (PasswordUtils.verifyPassword(password, passwordFromDatabase)){
                    ctx.header("HX-Redirect", "/user/home");
                    ctx.sessionAttribute("userEmail", email);
                    ctx.status(200);
                } 
                 else {
                    context.put("errorMessage", "Email or password is incorrect :(");
                    context.put("email", email);
                    context.put("password", password);
                    compiledTemplate.evaluate(writer, context);
                    String output = writer.toString();
                    ctx.result(output);
                }
            }
        }
    }
    private String generateInvalidDateResponse() throws IOException {
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/invalidDate.peb");
        HashMap <String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        context.put("message","Please enter a valid date.");
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();
        return output;
    }
    public static int getUserId(String email){
        int id = userService.getUserId(email);
        return id;
    }
    public static String getUserName(int id){
        String name = userService.getUserName(id);
        return name;
    }
    private int numberOfPosts(List<Post> posts){
        int counter = 0;
        for(Post p : posts){
            if (p.getRetweetId() == -1){
                counter++;
            }
        }
        return counter;
    }
}
