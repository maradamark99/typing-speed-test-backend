package com.maradamark09.typingspeedtest.word;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@AllArgsConstructor
@Getter
public class WordRequest {

    @NotNull
    @Length(min=1, max=50)
    private final String value;

    private final Long difficulty_id;

}
