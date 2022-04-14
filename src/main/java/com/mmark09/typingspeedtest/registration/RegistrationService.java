package com.mmark09.typingspeedtest.registration;

import com.mmark09.typingspeedtest.exceptions.InvalidRegistrationRequestException;
import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRole;
import com.mmark09.typingspeedtest.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public User register(RegistrationRequest request) {
        boolean isEmailValid = Validator.isEmailValid(request.getEmail());
        boolean isPasswordValid =  Validator.isPasswordValid(request.getPassword());
        boolean isUsernameValid =  Validator.isUsernameValid(request.getUsername());

        if(!isEmailValid || !isPasswordValid || !isUsernameValid)
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

}
