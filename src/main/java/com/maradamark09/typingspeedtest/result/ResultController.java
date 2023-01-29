package com.maradamark09.typingspeedtest.result;


import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.util.PaginationUtil;
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
    public List<ResultResponse> getAmountOf(@RequestParam(value = "page", defaultValue = PaginationUtil.DEFAULT_PAGE) Integer page,
                                            @RequestParam(value = "amount", defaultValue = PaginationUtil.DEFAULT_AMOUNT) Integer amount) {
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
