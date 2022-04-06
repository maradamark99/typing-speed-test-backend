package com.mmark09.typingspeedtest.registration;

import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRole;
import com.mmark09.typingspeedtest.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = Validator.isEmailValid(request.getEmail());
        boolean isPasswordValid =  Validator.isPasswordValid(request.getPassword());
        boolean isUsernameValid =  Validator.isUsernameValid(request.getUserName());

        if(!isEmailValid || !isPasswordValid || !isUsernameValid)
            throw new IllegalStateException("invalid input");

        return userService.signUpUser(
                new User(
                        request.getUserName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }

}
