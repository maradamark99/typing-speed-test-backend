package com.maradamark09.typingspeedtest.difficulty;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class DifficultyDTO {

    private long id;

    @NotNull
    @Length(min = 3, max = 255, message = "The length of the difficulty '${validatedValue}' must be between {min} and {max} characters.")
    private String value;

    @NotNull
    @Min(value = 4)
    private Byte maxWordLength;

}
