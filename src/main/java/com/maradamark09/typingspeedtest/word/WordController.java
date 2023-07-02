package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/random/{difficulty}")
    public ResponseEntity<List<String>> getRandomWordsByDifficulty(
            @PathVariable("difficulty") String difficulty,
            @RequestParam(value = "amount", defaultValue = PaginationUtil.DEFAULT_AMOUNT) Integer amount) {
        return ResponseEntity.ok(wordService.getRandomWordsByDifficulty(difficulty, amount));
    }

    @GetMapping("/{difficulty}")
    public ResponseEntity<List<String>> getAllByDifficulty(@PathVariable("difficulty") String difficulty) {
        return ResponseEntity.ok(wordService.getAllByDifficulty(difficulty));
    }

    @PostMapping
    public ResponseEntity<Word> save(@Valid @RequestBody WordRequest word) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wordService.save(word));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        wordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}