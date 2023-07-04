package com.maradamark09.typingspeedtest.word;

import org.springframework.stereotype.Service;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;

@Service
public class WordMapper {

    public WordDTO entityToDTO(Word entity) {
        return WordDTO
                .builder()
                .id(entity.getId())
                .value(entity.getValue())
                .difficulty_id(entity.getDifficulty().getId())
                .build();
    }

    public Word dtoToEntity(WordDTO dto, Difficulty difficulty) {
        return Word
                .builder()
                .value(dto.getValue())
                .difficulty(difficulty)
                .build();
    }
}
