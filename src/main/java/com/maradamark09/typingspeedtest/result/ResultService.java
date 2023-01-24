package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ResultService {

    // TODO: more

    List<Result> getAmountOf(Integer amount);

    List<Result> getByUserId(UUID userId) throws ResourceNotFoundException;

    void save(ResultRequest resultRequest) throws ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

}
