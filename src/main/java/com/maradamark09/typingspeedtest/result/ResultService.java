package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.User;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface ResultService {

    // TODO: more

    List<ResultResponse> getAmountOf(Integer page, Integer amount);

    List<ResultResponse> getByUserId(UUID userId) throws ResourceNotFoundException;

    void save(ResultRequest resultRequest, User user) throws ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

}
