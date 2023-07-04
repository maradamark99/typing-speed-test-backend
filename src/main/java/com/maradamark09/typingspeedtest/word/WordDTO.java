package com.maradamark09.typingspeedtest.word;

import org.hibernate.validator.constraints.Length;

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
public class WordDTO {

    private Long id;

    @NotNull
    @Length(min = 1, max = 50, message = "The length of the word must be between {min} and {max} characters.")
    private String value;

    @NotNull
    private Long difficulty_id;

}
