package com.maradamark09.typingspeedtest.result;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResultMapper {

    public ResultDTO entityToDto(Result entity) {

        return ResultDTO
                .builder()
                .id(entity.getId())
                .wpm(entity.getWpm())
                .accuracy(entity.getAccuracy().doubleValue())
                .username(entity.getUser().getUsername())
                .date(entity.getDate().toInstant().toEpochMilli())
                .difficulty(entity.getDifficulty().getValue())
                .build();
    }

    public Result dtoToEntity(ResultDTO dto, User user, Difficulty difficulty) {
        return Result.builder()
                .user(user)
                .accuracy(BigDecimal.valueOf(dto.getAccuracy()))
                .wpm(dto.getWpm())
                .difficulty(difficulty)
                .date(Timestamp.from(Instant.ofEpochMilli(dto.getDate())))
                .build();
    }

}
