package com.maradamark09.typingspeedtest.difficulty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class DifficultyRequest {

    @NotNull
    @Length(min = 1, max = 255)
    private String value;

    @NotNull
    private Byte maxWordLength;

    public DifficultyRequest(String value, Byte maxWordLength) {
        this.value = value.toLowerCase();
        this.maxWordLength = maxWordLength;
    }

}
