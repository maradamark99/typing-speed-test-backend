package com.maradamark09.typingspeedtest.difficulty;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/difficulties")
@RequiredArgsConstructor
public class DifficultyController {

    private final DifficultyService difficultyService;

    @GetMapping
    public ResponseEntity<List<Difficulty>> getAll() {
        return ResponseEntity.ok(difficultyService.findAll());
    }

    @PostMapping
    public ResponseEntity<Difficulty> save(@Valid @RequestBody DifficultyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(difficultyService.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        difficultyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Difficulty> updateById(@Valid @RequestBody DifficultyRequest difficulty,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(difficultyService.update(difficulty, id));
    }

}
