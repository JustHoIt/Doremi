package com.example.snsfood.util;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

//
@Service
public class PasswordUtils {

    public static boolean equals(String plaintext, String hashed) {

        if (plaintext == null || plaintext.length() < 1) {
            return false;
        }

        if (hashed == null || hashed.length() < 1) {
            return false;
        }

        return BCrypt.checkpw(plaintext, hashed);
    }

    public static String encPassword(String plaintext) {
        if (plaintext == null || plaintext.length() < 1) {
            return "";
        }
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

}
