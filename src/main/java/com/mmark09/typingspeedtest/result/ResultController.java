package com.mmark09.typingspeedtest.result;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/results")
@AllArgsConstructor
public class ResultController {
    private final ResultRepository resultRepository;

    @PostMapping
    public Result save(@RequestBody Result result) {
        return resultRepository.save(result);
    }

    @GetMapping("/my_results")
    public List<Result> getUserResults(@RequestParam String username) {
        return resultRepository.findByUsername(username);
    }

    @GetMapping
    public List<Result> getResults() {
        return resultRepository.findAll();
    }
}
