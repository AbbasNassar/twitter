package com.x;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        app.post("/users/email", this::HandleUsersEmail); // Returns partial HTML for user list
        app.post("/users/password", this::handlePasswordStrength);
        app.post("/users/addUser", this::addUser);
    }

    private void renderIndex(Context ctx) {
        // Render the main page template using Pebble
        ctx.render("templates/index.html");
    }

    private void HandleUsersEmail(Context ctx) {
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
    private void addUser(Context ctx){
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
        LocalDateTime CreatedAtTime = LocalDateTime.now();
        User user = new User(name, email, password, dateOfBirth, CreatedAtTime,CreatedAtTime);
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
    private String generateInvalidDateResponse() {
        return "<div class=\"birth-date\" id=\"dateForm\">" +
               "  <h3>Date of birth</h3>" +
               "  <span class=\"text-danger\">" +
               "    Please enter a valid date. This will not be shown publicly. Confirm your own age, even if this account is for a business, a pet, or something else." +
               "  </span>" +
               "  <SELECT id=\"month\" name=\"mm\" onchange=\"change_month(this)\">" +
               "    <!-- Options for months can be dynamically added here -->" +
               "  </SELECT>" +
               "  <SELECT id=\"day\" name=\"dd\">" +
               "    <!-- Options for days can be dynamically added here -->" +
               "  </SELECT>" +
               "  <SELECT id=\"year\" name=\"yyyy\" onchange=\"change_year(this)\">" +
               "    <!-- Options for years can be dynamically added here -->" +
               "  </SELECT>" +
               "</div>" +
               "<script src=\"Js/birth-date.js\"></script>";
    }
}
