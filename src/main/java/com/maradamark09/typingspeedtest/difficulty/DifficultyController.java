package com.maradamark09.typingspeedtest.difficulty;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/difficulties")
@RequiredArgsConstructor
public class DifficultyController {

    private final DifficultyService difficultyService;

    @GetMapping
    public List<Difficulty> getAll() { return difficultyService.findAll(); }

    @PostMapping("/difficulty")
    @ResponseStatus(HttpStatus.CREATED)
    public Difficulty save(@Valid @RequestBody DifficultyRequest request) {
        return difficultyService.save(request);
    }

    @DeleteMapping("/difficulty/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        difficultyService.deleteById(id);
    }

    @PutMapping("/difficulty/{id}")
    public Difficulty updateById(@Valid @RequestBody DifficultyRequest difficulty, @PathVariable("id") Long id) {
        return difficultyService.update(difficulty, id);
    }

}
