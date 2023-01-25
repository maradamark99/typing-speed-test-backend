package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    @Override
    public List<Result> getAmountOf(Integer page, Integer amount) {
        return resultRepository.findAll(PageRequest.of(page,amount)).toList();
    }

    @Override
    public List<Result> getByUserId(UUID userId) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("");
        return resultRepository.findResultByUser(userId);
    }

    @Override
    public void save(ResultRequest resultRequest) {

        var UUIDfromString = UUID.fromString(resultRequest.userId());

        var user = userRepository
                .findById(UUIDfromString)
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

}
