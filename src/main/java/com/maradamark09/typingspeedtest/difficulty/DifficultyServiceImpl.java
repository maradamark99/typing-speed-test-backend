package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
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
        if(difficultyRepository.existsByValue(difficultyRequest.getValue()))
            throw new ResourceAlreadyExistsException("The provided difficulty {" + difficultyRequest.getValue()
                    + "} already exists.");

        var difficultyToSave =
                Difficulty.builder()
                        .value(difficultyRequest.getValue())
                        .maxWordLength(difficultyRequest.getMaxWordLength())
                        .build();

        return difficultyRepository.save(difficultyToSave);
    }

    @Override
    public void deleteById(Long id) {
        if(!difficultyRepository.existsById(id))
            throw new ResourceNotFoundException("The difficulty with the given id: {" + id + "} does not exist.");
        difficultyRepository.deleteById(id);
    }

    @Override
    public Difficulty update(DifficultyRequest difficultyRequest, Long id) {
        return difficultyRepository.findById(id)
                .map(difficulty -> {
                    difficulty.setValue(difficultyRequest.getValue());
                    difficulty.setMaxWordLength(difficultyRequest.getMaxWordLength());
                    return difficultyRepository.save(difficulty);
                }).orElseGet(() -> save(difficultyRequest));
    }

}
