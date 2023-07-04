package com.maradamark09.typingspeedtest.result;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ResultDTO {

    private Long id;

    @NotNull
    @Range(min = 0, max = 350)
    private Short wpm;

    @NotNull
    @Range(min = 0, max = 100)
    private Double accuracy;

    private String username;

}
