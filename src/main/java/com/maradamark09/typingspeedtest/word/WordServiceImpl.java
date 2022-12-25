package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.exception.WordLengthGreaterThanDifficultyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService{

    private final WordRepository wordRepository;

    private final DifficultyRepository difficultyRepository;

    @Override
    public List<String> getAllByDifficulty(String difficulty) throws ResourceNotFoundException {
        checkIfDifficultyExists(difficulty);
        return wordRepository.findAllByDifficulty(difficulty);
    }

    @Override
    public List<String> getRandomWordsByDifficulty(String difficulty, Integer amount) throws ResourceNotFoundException {
        checkIfDifficultyExists(difficulty);
        return wordRepository.findRandomWordsByDifficulty(difficulty, Pageable.ofSize(amount));
    }

    @Override
    public Word save(WordRequest wordRequest) throws ResourceNotFoundException, WordLengthGreaterThanDifficultyException, ResourceAlreadyExistsException {
        var difficulty = difficultyRepository.findById(wordRequest.difficulty_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The provided difficulty_id: {" + wordRequest.difficulty_id() + "} does not match any difficulty."
                ));

        if(wordRequest.value().length() > difficulty.getMaxWordLength())
            throw new WordLengthGreaterThanDifficultyException(wordRequest.value(), difficulty);

        if(wordRepository.existsByValue(wordRequest.value()))
            throw new ResourceAlreadyExistsException("The given word: {"+ wordRequest.value() +"} already exists");

        var wordToSave =
                Word.builder()
                        .value(wordRequest.value())
                        .difficulty(difficulty)
                        .build();
        return wordRepository.save(wordToSave);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        wordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The requested word with" +
                " id: {" + id + "} does not exist."));
        wordRepository.deleteById(id);
    }

    private void checkIfDifficultyExists(String difficulty) throws ResourceNotFoundException {
        if(!difficultyRepository.existsByValue(difficulty.toLowerCase()))
            throw new ResourceNotFoundException("The given difficulty: {" + difficulty + "} does not exist");
    }

}
