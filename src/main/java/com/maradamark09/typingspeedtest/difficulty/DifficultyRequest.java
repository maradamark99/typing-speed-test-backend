package com.maradamark09.typingspeedtest.difficulty;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record DifficultyRequest(@NotNull @Length(min = 3, max = 255) String value, @NotNull Byte maxWordLength) {

    public DifficultyRequest(String value, Byte maxWordLength) {
        this.value = value.toLowerCase();
        this.maxWordLength = maxWordLength;
    }

}
