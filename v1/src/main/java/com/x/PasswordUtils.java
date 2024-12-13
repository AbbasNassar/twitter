package com.x;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    public static boolean verifyPassword(String inputPassword, String storedHash){
        return BCrypt.checkpw(inputPassword, storedHash);
    }

}
