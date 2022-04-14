package com.mmark09.typingspeedtest.result;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // for now, this will do
@RequestMapping(path = "api/v1/results")
@AllArgsConstructor
public class ResultController {
    private final ResultRepository resultRepository;

    @PostMapping
    public Result save(Result result) {
        return resultRepository.save(result);
    }

    @GetMapping
    public List<Result> getResults() {
        return resultRepository.findAll();
    }
}
