package com.mmark09.typingspeedtest.result;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultRepository extends MongoRepository<Result, String> {
    public List<Result> findByUsername(String username);
}
