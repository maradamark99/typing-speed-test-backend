package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.config.OpenAPIConfig;
import com.maradamark09.typingspeedtest.util.PaginationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Get randomized words of a given difficulty")
    @GetMapping("/random/{difficulty}")
    public ResponseEntity<List<String>> getRandomWordsByDifficulty(
            @PathVariable("difficulty") String difficulty,
            @RequestParam(value = "amount", defaultValue = PaginationUtil.DEFAULT_AMOUNT) Integer amount) {
        return ResponseEntity.ok(wordService.getRandomWordsByDifficulty(difficulty, amount));
    }

    @Operation(summary = "Get all words of a given difficulty")
    @GetMapping("/{difficulty}")
    public ResponseEntity<List<String>> getAllByDifficulty(@PathVariable("difficulty") String difficulty) {
        return ResponseEntity.ok(wordService.getAllByDifficulty(difficulty));
    }

    @Operation(summary = "Save a word")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @PostMapping
    public ResponseEntity<WordDTO> save(@Valid @RequestBody WordDTO word) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wordService.save(word));
    }

    @Operation(summary = "Delete a word by its id")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        wordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}