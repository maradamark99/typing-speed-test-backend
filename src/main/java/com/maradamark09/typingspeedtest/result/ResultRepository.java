package com.maradamark09.typingspeedtest.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findResultByUserId(UUID userId);

}
