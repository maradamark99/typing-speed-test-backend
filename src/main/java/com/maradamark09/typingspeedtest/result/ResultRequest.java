package com.maradamark09.typingspeedtest.result;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

public record ResultRequest(@NotNull @Range(min = 0, max = 350) Short wpm,
                            @NotNull @Range(min = 0, max = 100) Double accuracy,

                            @NotNull UUID user_id) { }
