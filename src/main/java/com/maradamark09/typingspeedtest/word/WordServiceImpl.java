package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.DifficultyNotFoundException;
import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordMapper mapper;

    private final WordRepository wordRepository;

    private final DifficultyRepository difficultyRepository;

    @Override
    public List<String> getAllByDifficulty(String difficulty) throws ResourceNotFoundException {
        if (!difficultyRepository.existsByValueIgnoreCase(difficulty))
            throw new DifficultyNotFoundException();
        return wordRepository.findAllByDifficulty(difficulty);
    }

    @Override
    public List<String> getRandomWordsByDifficulty(String difficulty, Integer amount) throws ResourceNotFoundException {

        if (!difficultyRepository.existsByValueIgnoreCase(difficulty))
            throw new DifficultyNotFoundException();

        return wordRepository.findRandomWordsByDifficulty(difficulty, Pageable.ofSize(amount));
    }

    @Override
    public WordDTO save(WordDTO wordDTO)
            throws ResourceNotFoundException, WordLengthGreaterThanDifficultyException, ResourceAlreadyExistsException {
        var difficulty = difficultyRepository.findById(wordDTO.getDifficulty_id())
                .orElseThrow(() -> new DifficultyNotFoundException(wordDTO.getDifficulty_id()));

        if (wordDTO.getValue().length() > difficulty.getMaxWordLength())
            throw new WordLengthGreaterThanDifficultyException(wordDTO.getValue(), difficulty);

        if (wordRepository.existsByValue(wordDTO.getValue()))
            throw new WordAlreadyExistsException(wordDTO.getValue());

        return mapper.entityToDTO(wordRepository.save(mapper.dtoToEntity(wordDTO, difficulty)));
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!wordRepository.existsById(id)) {
            throw new WordNotFoundException(id);
        }
        wordRepository.deleteById(id);
    }

}
