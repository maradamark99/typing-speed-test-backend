package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultMapper mapper;

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    @Override
    public List<ResultDTO> getAmountOf(PageRequest pageRequest) {
        return resultRepository
                .findAll(pageRequest)
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public List<ResultDTO> getByUserId(UUID userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        return resultRepository
                .findResultByUserId(userId)
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public void save(ResultDTO resultRequest, User user) {
        resultRepository.save(mapper.dtoToEntity(resultRequest, user));
    }

    @Override
    public void deleteById(Long id) {
        if (!resultRepository.existsById(id))
            throw new ResultNotFoundException(id);
        resultRepository.deleteById(id);
    }

}
