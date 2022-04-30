package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public Word getAllWordsByDifficulty(Difficulty difficulty) {
        return wordRepository.findAllByDifficulty(difficulty);
    }

    public Word save(Word word) {
        return wordRepository.save(word);
    }
}
