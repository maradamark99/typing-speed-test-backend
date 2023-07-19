package com.maradamark09.typingspeedtest.result;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Range;

import com.maradamark09.typingspeedtest.difficulty.DifficultyDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ResultDTO {

    private Long id;

    @NotNull
    @Range(min = 0, max = 350)
    private Short wpm;

    @NotNull
    @Range(min = 0, max = 100)
    private Double accuracy;

    private String username;

    @NotNull
    private Long date;

    @NotBlank
    private String difficulty;

}
