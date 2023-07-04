package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    @Override
    public List<ResultDTO> getAmountOf(PageRequest pageRequest) {
        return resultRepository
                .findAll(pageRequest)
                .stream()
                .map((result) -> entityToDto(result))
                .toList();
    }

    @Override
    public List<ResultDTO> getByUserId(UUID userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        return resultRepository
                .findResultByUserId(userId)
                .stream()
                .map((result) -> entityToDto(result))
                .toList();
    }

    @Override
    public void save(ResultDTO resultRequest, User user) {
        resultRepository.save(dtoToEntity(resultRequest, user));
    }

    @Override
    public void deleteById(Long id) {
        if (!resultRepository.existsById(id))
            throw new ResultNotFoundException(id);
        resultRepository.deleteById(id);
    }

    @Override
    public ResultDTO entityToDto(Result entity) {
        return ResultDTO
                .builder()
                .id(entity.getId())
                .wpm(entity.getWpm())
                .accuracy(entity.getAccuracy().doubleValue())
                .username(entity.getUser().getUsername())
                .build();
    }

    @Override
    public Result dtoToEntity(ResultDTO dto, User user) {
        return Result.builder()
                .user(user)
                .accuracy(BigDecimal.valueOf(dto.getAccuracy()))
                .wpm(dto.getWpm())
                .build();
    }
}
