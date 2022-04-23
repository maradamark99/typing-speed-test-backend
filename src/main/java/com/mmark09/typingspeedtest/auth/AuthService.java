package com.mmark09.typingspeedtest.auth;

import com.mmark09.typingspeedtest.exceptions.InvalidRegistrationRequestException;
import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRole;
import com.mmark09.typingspeedtest.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    private boolean isValidRegistrationRequest(RegistrationRequest request) {
        return Validator.isEmailValid(request.getEmail()) &&
                Validator.isPasswordValid(request.getPassword()) &&
                Validator.isUsernameValid(request.getUsername());
    }

    public User register(RegistrationRequest request) {

        if(!isValidRegistrationRequest(request))
            throw new InvalidRegistrationRequestException();

        return userService.signUpUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }

    public User login(LoginRequest request) {
        return userService.loadUserByUsername(request.getUsername());
    }
}
