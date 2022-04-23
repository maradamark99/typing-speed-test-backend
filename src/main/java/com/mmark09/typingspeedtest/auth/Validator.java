package com.mmark09.typingspeedtest.auth;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern emailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern usernameRegex =
            Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

    private static final Pattern passwordRegex =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    public static boolean isEmailValid(String email){
        return emailRegex
                .matcher(email)
                .matches();
    }

    public static boolean isUsernameValid(String username){
        return usernameRegex
                .matcher(username)
                .matches();
    }

    public static boolean isPasswordValid(String password){
        return passwordRegex
                .matcher(password)
                .matches();
    }
}
