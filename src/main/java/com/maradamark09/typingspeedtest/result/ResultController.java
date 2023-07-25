package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.config.OpenAPIConfig;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @Operation(summary = "Get results of a user by user id")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ResultDTO>> getByUserId(@PathVariable("id") UUID userId) {
        return ResponseEntity.ok(resultService.getByUserId(userId));
    }

    @Operation(summary = "Get results by page")
    @GetMapping
    public ResponseEntity<PageResponse<ResultDTO>> getAmountOf(
            @PageableDefault(size = 5, direction = Sort.Direction.DESC, sort = "date") Pageable pageable) {
        return ResponseEntity.ok(resultService.getAmountOf(pageable));
    }

    @Operation(summary = "Save a result")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ResultDTO resultDTO,
            @AuthenticationPrincipal User user) {
        resultService.save(resultDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete a result by its id")
    @SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME_NAME)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        resultService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
