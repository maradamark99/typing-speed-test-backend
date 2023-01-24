package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name = "results")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Range(min = 0, max = 350)
    private Short wpm;

    @NotNull
    @Range(min = 0, max = 100)
    private BigDecimal accuracy;

    private final Timestamp timestamp = Timestamp.from(ZonedDateTime.now().toInstant());

    @ManyToOne
    @NotNull
    private User user;

}
