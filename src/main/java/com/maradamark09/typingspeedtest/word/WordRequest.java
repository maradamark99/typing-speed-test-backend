package com.maradamark09.typingspeedtest.word;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@AllArgsConstructor
@Getter
@Setter
public class WordRequest {

    @NotNull
    @Length(min=1, max=50)
    private String value;

    private Long difficulty_id;

}
