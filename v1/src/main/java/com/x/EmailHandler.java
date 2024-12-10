package com.x;

import java.util.List;
import java.util.regex.Pattern;

public class EmailHandler {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Constructor
    public EmailHandler() {
    }

    // Method to verify if an email matches the regex
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    // Method to check if an email exists in the list
    public static boolean isEmailInList(String email, List<String> emailList) {
        return emailList.contains(email);
    }
    public static String generateInvalidEmailResponse(String email) {
        return "<div class=\"form-floating mb-3 black-theme\" hx-target=\"this\" hx-swap=\"outerHTML\">"
             + "<input type=\"email\" class=\"form-control is-invalid\" id=\"emailInput\" name=\"email\" hx-post=\"/users/email\" value=" +email +">"
             + "<label for=\"floatingInput\" class=\"up-form-desc\">Email</label>"
             + "<div class=\"invalid-feedback\">Please enter a valid email address.</div>"
             + "</div>";
    }
    public static String generateEmailExistsResponse(String email) {
        return "<div class=\"form-floating mb-3 black-theme\" hx-target=\"this\" hx-swap=\"outerHTML\">"
             + "<input type=\"email\" class=\"form-control is-invalid\" id=\"emailInput\" name=\"email\" hx-post=\"/users/email\" value=" +email +">"
             + "<label for=\"floatingInput\" class=\"up-form-desc\">Email</label>"
             + "<div class=\"invalid-feedback\">This email is already linked to an account.</div>"
             + "</div>";
    }
    public static String generateValidEmailResponse(String email) {
        return "<div class=\"form-floating mb-3 black-theme\" hx-target=\"this\" hx-swap=\"outerHTML\">"
             + "<input type=\"email\" class=\"form-control\" id=\"emailInput\" name=\"email\" hx-post=\"/users/email\" value=" +email +">"
             + "<label for=\"floatingInput\" class=\"up-form-desc\">Email</label>"
             + "</div>";
    }

    
}
