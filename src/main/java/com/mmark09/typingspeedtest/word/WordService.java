package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<Word> getAllWordsByDifficulty(String difficulty) {
        difficulty = difficulty.toUpperCase();
        if(!isValidDifficulty(difficulty))
            throw new IllegalArgumentException("Invalid difficulty");
        return wordRepository.findWordsByDifficulty(difficulty);
    }

    public Word save(Word word) {
        if(word.getWord().length() < 1 || word.getWord().length() > 30)
            throw new IllegalArgumentException("Word must be between 1 and 30 characters");
        return wordRepository.save(word);
    }

    private boolean isValidDifficulty(String difficulty) {
        for(var d : Difficulty.values())
            if(d.name().equals(difficulty))
                return true;
        return false;
    }

}
