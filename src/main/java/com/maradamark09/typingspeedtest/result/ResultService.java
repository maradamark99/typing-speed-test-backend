package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.User;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

public interface ResultService {

    List<ResultResponse> getAmountOf(PageRequest pageRequest);

    List<ResultResponse> getByUserId(UUID userId) throws ResourceNotFoundException;

    void save(ResultRequest resultRequest, User user) throws ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

}
