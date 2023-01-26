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

    @GetMapping("/random/{difficulty}")
    public List<String> getRandomWordsByDifficulty(
            @PathVariable("difficulty") String difficulty,
            @RequestParam(value = "amount", defaultValue = "1") Integer amount) {
        return wordService.getRandomWordsByDifficulty(difficulty, amount);
    }

    @GetMapping("/{difficulty}")
    public List<String> getAllByDifficulty(@PathVariable("difficulty") String difficulty) {
        return wordService.getAllByDifficulty(difficulty);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Word save(@Valid @RequestBody WordRequest word){
        return wordService.save(word);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        wordService.deleteById(id);
    }

}