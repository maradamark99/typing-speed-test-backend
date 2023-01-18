package com.maradamark09.typingspeedtest.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(@Length(min = 4, max = 50, message =
        "The length of the username must be between {min} and {max} characters") String username,
                                  @NotEmpty @Email(message = "The given email: '${validatedValue}' is not a valid email.",
                                          regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email,
                                  @NotEmpty @Length(min = 8, max = 255, message =
                                          "The password must be at least {min} characters long") String password) {

}
