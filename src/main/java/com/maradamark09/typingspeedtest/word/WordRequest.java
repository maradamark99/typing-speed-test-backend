package com.maradamark09.typingspeedtest.word;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record WordRequest(@NotNull @Length(min = 1, max = 50) String value, Long difficulty_id) {
}
