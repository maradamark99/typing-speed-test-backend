package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/words")
@AllArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/{difficulty}")
    public Word getAllByDifficulty(@PathVariable(value="difficulty") Difficulty difficulty) {
        return wordService.getAllWordsByDifficulty(difficulty);
    }

    @PostMapping
    public Word save(@RequestBody Word word) {
        return wordService.save(word);
    }
}
