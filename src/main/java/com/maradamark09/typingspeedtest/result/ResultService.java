package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.User;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

public interface ResultService {

    List<ResultDTO> getAmountOf(PageRequest pageRequest);

    List<ResultDTO> getByUserId(UUID userId) throws ResourceNotFoundException;

    void save(ResultDTO resultRequest, User user) throws ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

    ResultDTO entityToDto(Result entity);

    Result dtoToEntity(ResultDTO dto, User user);

}
