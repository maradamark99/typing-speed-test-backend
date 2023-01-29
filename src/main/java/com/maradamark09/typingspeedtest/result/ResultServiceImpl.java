package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;
import com.maradamark09.typingspeedtest.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

        var totalAmount = resultRepository.count();

        if (!PaginationUtil.isPageValid(page, totalAmount, amount))
            throw new IllegalArgumentException("The given page is invalid.");

        var result = resultRepository
                .findAll(PageRequest.of(page,amount))
                .stream();

        return resultStreamToResultResponseListMapper(result);

    }

    @Override
    public List<ResultResponse> getByUserId(UUID userId) {

        if(!userRepository.existsById(userId))
            throw new UserNotFoundException(userId);

        var resultStream = resultRepository
                .findResultByUserId(userId)
                .stream();

        return resultStreamToResultResponseListMapper(resultStream);
    }
    @Override
    public void save(ResultRequest resultRequest, User user) {

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
            throw new ResultNotFoundException(id);
        resultRepository.deleteById(id);
    }

    private List<ResultResponse> resultStreamToResultResponseListMapper(Stream<Result> resultStream) {
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
