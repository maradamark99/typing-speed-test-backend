package com.maradamark09.typingspeedtest.word;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/{difficulty}/{amount}")
    public List<String> getRandomWordsByDifficulty(
            @PathVariable("difficulty") String difficulty,
            @PathVariable("amount") Integer amount) {
        return wordService.getRandomWordsByDifficulty(difficulty, amount);
    }

    @GetMapping("/{difficulty}")
    public List<String> getAllByDifficulty(@PathVariable("difficulty") String difficulty) {
        return wordService.getAllByDifficulty(difficulty);
    }

    @PostMapping("/word")
    @ResponseStatus(HttpStatus.CREATED)
    public Word save(@Valid @RequestBody WordRequest word){
        return wordService.save(word);
    }

    @DeleteMapping("/word/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        wordService.deleteById(id);
    }

}