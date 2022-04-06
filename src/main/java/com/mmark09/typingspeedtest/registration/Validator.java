package com.mmark09.typingspeedtest.registration;

import java.util.regex.Pattern;

public class Validator {

    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]" +
            "+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";

    private static final String usernameRegex = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static boolean isEmailValid(String email){
        return Pattern
                .compile(emailRegex)
                .matcher(email)
                .matches();
    }

    public static boolean isUsernameValid(String username){
        return Pattern
                .compile(usernameRegex)
                .matcher(username)
                .matches();
    }

    public static boolean isPasswordValid(String password){
        return Pattern
                .compile(passwordRegex)
                .matcher(password)
                .matches();
    }
}
