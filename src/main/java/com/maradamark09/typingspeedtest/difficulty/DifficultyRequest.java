package com.maradamark09.typingspeedtest.difficulty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class DifficultyRequest {

    @NotNull
    @Length(min = 1, max = 255)
    private String value;

    @NotNull
    private Byte maxWordLength;

}
