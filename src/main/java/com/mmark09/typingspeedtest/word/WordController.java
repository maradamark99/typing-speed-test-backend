package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/words")
@AllArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/{difficulty}")
    public List<Word> getAllByDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return wordService.getAllWordsByDifficulty(difficulty);
    }

    @PostMapping
    public Word save(@RequestBody Word word) {
        return wordService.save(word);
    }
}
