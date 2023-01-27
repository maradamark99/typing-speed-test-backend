package com.maradamark09.typingspeedtest.result;


import com.maradamark09.typingspeedtest.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ResultResponse> getAmountOf(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "amount", defaultValue = "10") Integer amount) {
        return resultService.getAmountOf(page, amount);
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
