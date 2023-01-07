package com.maradamark09.typingspeedtest.auth;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
public record LoginRequest(@Length(min = 4, max = 50) String username,
                           @NotEmpty @Length(min = 8, max = 255) String password) {
}

