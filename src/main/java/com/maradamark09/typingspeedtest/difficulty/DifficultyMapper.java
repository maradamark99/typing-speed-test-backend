package com.maradamark09.typingspeedtest.difficulty;

import org.springframework.stereotype.Component;

@Component
public class DifficultyMapper {

    public DifficultyDTO entityToDto(Difficulty entity) {
        return DifficultyDTO
                .builder()
                .id(entity.getId())
                .value(entity.getValue())
                .maxWordLength(entity.getMaxWordLength())
                .build();
    }

    public Difficulty dtoToEntity(DifficultyDTO dto) {
        return Difficulty
                .builder()
                .value(dto.getValue())
                .maxWordLength(dto.getMaxWordLength())
                .build();
    }

}
