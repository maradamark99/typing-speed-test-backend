package com.maradamark09.typingspeedtest.word;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record WordRequest(@NotNull @Length(min = 1, max = 50,
        message = "The length of the word must be between {min} and {max} characters.")
                          String value,
                          Long difficulty_id) {
}
