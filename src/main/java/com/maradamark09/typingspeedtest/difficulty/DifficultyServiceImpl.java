package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyRepository difficultyRepository;

    @Override
    public List<Difficulty> findAll() {
        return difficultyRepository.findAll();
    }

    @Override
    public Difficulty save(DifficultyRequest difficultyRequest) {
        if(difficultyRepository.existsByValue(difficultyRequest.value()))
            throw new DifficultyAlreadyExistsException(difficultyRequest.value());

        var difficultyToSave =
                Difficulty.builder()
                        .value(difficultyRequest.value())
                        .maxWordLength(difficultyRequest.maxWordLength())
                        .build();

        return difficultyRepository.save(difficultyToSave);
    }

    @Override
    public void deleteById(Long id) {
        if(!difficultyRepository.existsById(id))
            throw new DifficultyNotFoundException(id);
        difficultyRepository.deleteById(id);
    }

    @Override
    public Difficulty update(DifficultyRequest difficultyRequest, Long id) {
        return difficultyRepository.findById(id)
                .map(difficulty -> {
                    difficulty.setValue(difficultyRequest.value());
                    difficulty.setMaxWordLength(difficultyRequest.maxWordLength());
                    return difficultyRepository.save(difficulty);
                }).orElseGet(() -> save(difficultyRequest));
    }

}
