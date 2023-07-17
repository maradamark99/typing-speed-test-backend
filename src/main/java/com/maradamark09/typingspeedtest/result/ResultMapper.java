package com.maradamark09.typingspeedtest.result;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResultMapper {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ResultDTO entityToDto(Result entity) {

        return ResultDTO
                .builder()
                .id(entity.getId())
                .wpm(entity.getWpm())
                .accuracy(entity.getAccuracy().doubleValue())
                .username(entity.getUser().getUsername())
                .timestamp(DATE_FORMAT.format(entity.getTimestamp()))
                .difficulty(entity.getDifficulty().getValue())
                .build();
    }

    public Result dtoToEntity(ResultDTO dto, User user, Difficulty difficulty) {
        return Result.builder()
                .user(user)
                .accuracy(BigDecimal.valueOf(dto.getAccuracy()))
                .wpm(dto.getWpm())
                .difficulty(difficulty)
                .build();
    }

}
