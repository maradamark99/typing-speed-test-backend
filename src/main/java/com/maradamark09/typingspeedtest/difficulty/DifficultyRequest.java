package com.maradamark09.typingspeedtest.difficulty;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record DifficultyRequest(@NotNull @Length(
        min = 3,
        max = 255,
        message = "The length of the difficulty '${validatedValue}' must be between {min} and {max} characters."
) String value, @NotNull Byte maxWordLength) { }
