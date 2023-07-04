package com.maradamark09.typingspeedtest.result;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.maradamark09.typingspeedtest.user.User;

@Component
public class ResultMapper {

    public ResultDTO entityToDto(Result entity) {
        return ResultDTO
                .builder()
                .id(entity.getId())
                .wpm(entity.getWpm())
                .accuracy(entity.getAccuracy().doubleValue())
                .username(entity.getUser().getUsername())
                .build();
    }

    public Result dtoToEntity(ResultDTO dto, User user) {
        return Result.builder()
                .user(user)
                .accuracy(BigDecimal.valueOf(dto.getAccuracy()))
                .wpm(dto.getWpm())
                .build();
    }

}
