package com.spring.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {
    // Encryte Password with BCryptPasswordEncoder
    public static String encryptedPassword(String password) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static  void test(String password) {
        var encryptedPassword = encryptedPassword(password);
        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}
