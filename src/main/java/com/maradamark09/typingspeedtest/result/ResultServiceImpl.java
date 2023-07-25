package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.difficulty.DifficultyNotFoundException;
import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;
import com.maradamark09.typingspeedtest.util.PageResponse;
import com.maradamark09.typingspeedtest.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultMapper mapper;

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    private final DifficultyRepository difficultyRepository;

    @Override
    public PageResponse<ResultDTO> getAmountOf(Pageable pageable) {
        var pageRequest = PaginationUtil.convertPageableToPageRequest(pageable);

        var page = resultRepository
                .findAll(pageRequest)
                .map(mapper::entityToDto);

        return new PageResponse<ResultDTO>(
                page.getContent(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber(),
                page.isLast(),
                page.isFirst());
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
        Difficulty difficulty = difficultyRepository.findByValueIgnoreCase(resultRequest.getDifficulty())
                .orElseThrow(() -> new DifficultyNotFoundException());
        resultRepository.save(mapper.dtoToEntity(resultRequest, user, difficulty));
    }

    @Override
    public void deleteById(Long id) {
        if (!resultRepository.existsById(id))
            throw new ResultNotFoundException(id);
        resultRepository.deleteById(id);
    }

}
