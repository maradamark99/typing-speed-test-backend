package com.mmark09.typingspeedtest.result;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{username}")
    public List<Result> getUserResults(@PathVariable(value = "username") String username) {
        return resultRepository.findByUsername(username);
    }

    @GetMapping
    public List<Result> getResults() {
        return resultRepository.findAll();
    }
}
