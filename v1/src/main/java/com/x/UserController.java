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
   

    private static UserService userService;
    PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();


    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/", this::renderIndex);          // Render main page
        app.post("/users/email", this::HandleUsersEmail); // Returns partial HTML for user list
        app.post("/users/password", this::handlePasswordStrength);
        app.post("/users/addUser", this::addUser);
        app.post("/users/logInUser", this::logInUser);
        app.get("/user/home",this::renderUserPage);
        app.post("/users/search",this::searchUsers);
    }

    private void renderIndex(Context ctx) {
       ctx.render("templates/index.html");
    }
    private void renderUserPage(Context ctx) throws IOException{
        String email = ctx.sessionAttribute("userEmail");
        User logedInUser = userService.getUser(email);       
        String userName = logedInUser.getName();
        String userUserName = logedInUser.getName() + "Xo";
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/home.peb");
        HashMap<String, Object> context = new HashMap<>();
        context.put("name", userName);
        context.put("username", userUserName);
        context.put("userEmail", email);
        context.put("ProfileImgSource", "/img/X_logo.jpg");
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();
        ctx.html(output);
    }
    private void searchUsers(Context ctx) throws IOException{
        System.out.println("hello");
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
                    context.put("username", u.getName() + "Xo");
                    context.put("ProfileImgSource", "/img/X_logo.jpg");  
                    compiledTemplate.evaluate(writer, context); 
                    output = writer.toString();
                }
                ctx.result(output);     
                
            }
        }
    }   

    private void HandleUsersEmail(Context ctx) throws IOException {
        String email = ctx.formParam("email");
        boolean isValidEmail = EmailHandler.isValidEmail(email);
        if (isValidEmail == true){
            boolean isEmailExists = EmailHandler.isEmailInList(email, userService.getAllEmails());
            if (isEmailExists) {
                ctx.result(EmailHandler.generateEmailExistsResponse(email));
            }
            else{
                ctx.result(EmailHandler.generateValidEmailResponse(email));
            }
        }
        else {
            ctx.result(EmailHandler.generateInvalidEmailResponse(email));
        }
    }
    private void handlePasswordStrength(Context ctx) {
        String password = ctx.formParam("password");
        //System.out.println(password);

        if (password == null || password.isEmpty()) {
            ctx.result(PasswordStrengthHandler.generatePasswordResponse("Password cannot be empty.", "text-danger",password));
        } else {
            String strength = PasswordStrengthHandler.evaluatePasswordStrength(password);
            String cssClass = PasswordStrengthHandler.getStrengthClass(strength);
            ctx.result(PasswordStrengthHandler.generatePasswordResponse("Password strength: " + strength, cssClass,password));
        }
    }
    private void addUser(Context ctx) throws IOException{
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String day = ctx.formParam("dd");
        String month = ctx.formParam("mm");
        String year = ctx.formParam("yyyy");
        if (year.equals("year") || month.equals("month") || day.equals("day")){
            ctx.result(generateInvalidDateResponse());
        }
        else {
        if (day.length() == 1)
            day = "0" + day;
        if (month.length() == 1)
            month = "0" + month;    
        String birthDate = year + "-" + month + "-" + day;
        LocalDate dateOfBirth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime editedAtTime = LocalDateTime.now();
        User user = new User(0,name, email, password, dateOfBirth, editedAtTime,editedAtTime);
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
                    System.out.println(email);
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
}
