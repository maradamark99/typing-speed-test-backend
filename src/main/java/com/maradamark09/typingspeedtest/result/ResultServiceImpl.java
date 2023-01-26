package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    @Override
    public List<ResultResponse> getAmountOf(Integer page, Integer amount) {

        var resultStream = resultRepository
                .findAll(PageRequest.of(page,amount))
                .getContent()
                .stream();

        return resultToResultResponseMapper(resultStream);

    }

    @Override
    public List<ResultResponse> getByUserId(UUID userId) {

        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("");

        var resultStream = resultRepository
                .findResultByUserId(userId)
                .stream();

        return resultToResultResponseMapper(resultStream);
    }
    @Override
    public void save(ResultRequest resultRequest) {

        // check if the given user id matches the token
        // or get user id from token

        var UUIDFromString = UUID.fromString(resultRequest.userId());

        var user = userRepository
                .findById(UUIDFromString)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        var resultToSave =
                Result.builder()
                        .user(user)
                        .wpm(resultRequest.wpm())
                        .accuracy(BigDecimal.valueOf(resultRequest.accuracy()))
                        .build();

        resultRepository.save(resultToSave);

    }

    @Override
    public void deleteById(Long id) {
        if(!resultRepository.existsById(id))
            throw new ResourceNotFoundException("");
        resultRepository.deleteById(id);
    }

    private List<ResultResponse> resultToResultResponseMapper(Stream<Result> resultStream) {
        return resultStream.map(
                r -> new ResultResponse(
                        r.getId(),
                        r.getWpm(),
                        r.getAccuracy().doubleValue(),
                        r.getUser().getUsername()
                ))
                .toList();
    }
}
