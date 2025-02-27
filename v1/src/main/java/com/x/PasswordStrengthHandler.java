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
}
