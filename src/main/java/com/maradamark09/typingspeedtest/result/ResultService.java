package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.util.PageResponse;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.converters.models.Pageable;

public interface ResultService {

    PageResponse<ResultDTO> getAmountOf(Pageable pageable);

    List<ResultDTO> getByUserId(UUID userId) throws ResourceNotFoundException;

    void save(ResultDTO resultRequest, User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;

    void deleteById(Long id) throws ResourceNotFoundException;

}
