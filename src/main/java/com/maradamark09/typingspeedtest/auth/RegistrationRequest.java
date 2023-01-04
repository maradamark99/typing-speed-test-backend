package com.maradamark09.typingspeedtest.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(@Length(min = 4, max = 50) String username,
                                  @NotEmpty @Email(message = "Email is not valid!",
                                          regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email,
                                  @NotEmpty @Length(min = 8, max = 255) String password) {

}
