package com.x;

public class PasswordStrengthHandler {

    public PasswordStrengthHandler(){

    }

    public static String evaluatePasswordStrength(String password) {
        int length = password.length();
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if (length >= 12 && hasLower && hasUpper && hasDigit && hasSpecial) {
            return "Strong";
        } else if (length >= 8 && ((hasLower && hasUpper) || (hasDigit && hasSpecial))) {
            return "Medium";
        } else {
            return "Weak";
        }
    }

    public static String getStrengthClass(String strength) {
        return switch (strength) {
            case "Strong" -> "text-success";
            case "Medium" -> "text-warning";
            default -> "text-danger";
        };
    }

    public static String generatePasswordResponse(String message, String cssClass, String password) {
        return "<div class=\"form-floating mb-3 black-theme\" hx-target=\"this\" hx-swap=\"outerHTML\">"
        + "<input type=\"password\" class=\"form-control\" id=\"passwordInput\" name=\"password\" "
        + "placeholder=\"Password\" hx-post=\"/users/password\" value=\"" + password + "\">"
        + "<label for=\"passwordInput\" class=\"up-form-desc\">Password</label>"
        + "<div id=\"passwordStrength\" class=\"form-text " + cssClass + "\">" + message + "</div>"
        + "</div>"
        + "<script>"
        + "document.getElementById('registerButton').disabled = " + (!cssClass.equals("text-success")) + ";"
        + "</script>"; 
    }
}
