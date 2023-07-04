package com.maradamark09.typingspeedtest.difficulty;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maradamark09.typingspeedtest.config.OpenAPIConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/difficulties")
@RequiredArgsConstructor
public class DifficultyController {

    private final DifficultyService difficultyService;

    @Operation(summary = "Get all difficulties")
    @GetMapping
    public ResponseEntity<List<DifficultyDTO>> getAll() {
        return ResponseEntity.ok(difficultyService.findAll());
    }

    @Operation(summary = "Create a new difficulty")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @PostMapping
    public ResponseEntity<DifficultyDTO> save(@Valid @RequestBody DifficultyDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(difficultyService.save(request));
    }

    @Operation(summary = "Delete a difficulty by its id")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        difficultyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a difficulty by its id")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @PutMapping("/{id}")
    public ResponseEntity<DifficultyDTO> updateById(@Valid @RequestBody DifficultyDTO difficulty,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(difficultyService.update(difficulty, id));
    }

}
