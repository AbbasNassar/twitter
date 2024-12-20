package com.x;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class EmailHandler {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    static PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();
    static PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/EmailResponse.peb");
    
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
    
    public static String generateInvalidEmailResponse(String email) throws IOException {
        HashMap<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        
        // Set invalid-email to true and provide a message
        context.put("invalidEmail", true);
        context.put("message", "Please enter a valid email address.");
        context.put("email", email);
        
        compiledTemplate.evaluate(writer, context);
        return writer.toString();
    }

    public static String generateEmailExistsResponse(String email) throws IOException {
        HashMap<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        
        // Set invalid-email to true and provide a message for existing email
        context.put("invalidEmail", true);
        context.put("message", "This email is already linked to an account.");
        context.put("email", email);
        
        compiledTemplate.evaluate(writer, context);
        return writer.toString();
    }

    public static String generateValidEmailResponse(String email) throws IOException {
        HashMap<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        
        // invalid-email is false, so no message will be displayed
        context.put("invalidEmail", false);
        context.put("email", email);
        
        compiledTemplate.evaluate(writer, context);
        return writer.toString();
    }
}
