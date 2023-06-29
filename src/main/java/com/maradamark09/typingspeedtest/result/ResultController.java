package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/user/{id}")
    public List<ResultResponse> getByUserId(@PathVariable("id") UUID userId) {
        return resultService.getByUserId(userId);
    }

    @GetMapping
    public List<ResultResponse> getAmountOf(Pageable pageable) {
        return resultService.getAmountOf(PageRequest.of(pageable.getPage(), pageable.getSize()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody ResultRequest resultRequest, @AuthenticationPrincipal User user) {
        resultService.save(resultRequest, user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(Long id) {
        resultService.deleteById(id);
    }

}
