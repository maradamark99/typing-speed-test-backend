package com.mmark09.typingspeedtest.word;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Word>> getAllByDifficulty(@PathVariable("difficulty") String difficulty) {
        try{
            return ResponseEntity.ok().body(wordService.getAllWordsByDifficulty(difficulty));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().header("Error message", e.getMessage()).build();
        }
    }

    @PostMapping
    public ResponseEntity<Word> save(@RequestBody Word word) {
        try{
            return ResponseEntity.ok().body(wordService.save(word));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
