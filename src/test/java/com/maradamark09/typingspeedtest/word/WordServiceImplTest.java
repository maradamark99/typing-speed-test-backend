package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @InjectMocks
    WordServiceImpl wordService;

    @Mock
    WordRepository wordRepository;

    @Mock
    DifficultyRepository difficultyRepository;

    private final WordMapper mapper = new WordMapper();

    @BeforeEach
    void init() {
        wordService = new WordServiceImpl(mapper, wordRepository, difficultyRepository);
    }

    @Test
    public void whenGetAllByDifficulty_andDifficultyExists_thenSuccess() {

        String difficulty = WordDataProvider.VALID_DIFFICULTY;
        List<String> expected = WordDataProvider.WORD_RESPONSE;

        when(difficultyRepository.existsByValue(difficulty)).thenReturn(true);
        when(wordRepository.findAllByDifficulty(difficulty)).thenReturn(expected);

        List<String> actual = wordService.getAllByDifficulty(difficulty);

        assertThat(actual).containsExactlyElementsOf(expected);
        verify(difficultyRepository).existsByValue(difficulty);
        verify(wordRepository).findAllByDifficulty(difficulty);

    }

    @Test
    public void whenGetAllByDifficulty_andDifficultyDoesNotExist_thenThrowsException() {

        String difficulty = WordDataProvider.VALID_DIFFICULTY;
        when(difficultyRepository.existsByValue(difficulty)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> wordService.getAllByDifficulty(difficulty));

        verify(difficultyRepository).existsByValue(difficulty);
        verify(wordRepository, never()).findAllByDifficulty(difficulty);

    }

    @Test
    public void whenGetRandomByDifficulty_andDifficultyDoesNotExist_thenThrowsException() {

        String difficulty = WordDataProvider.VALID_DIFFICULTY;
        int amount = WordDataProvider.AMOUNT_OF;
        when(difficultyRepository.existsByValue(difficulty)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> wordService.getRandomWordsByDifficulty(difficulty, amount));

        verify(difficultyRepository).existsByValue(difficulty);
        verify(wordRepository, never()).findRandomWordsByDifficulty(difficulty, Pageable.ofSize(amount));

    }

    @Test
    public void whenDeleteById_andWordExistsById_thenSuccess() {

        long id = WordDataProvider.VALID_WORD_ID;

        when(wordRepository.existsById(id)).thenReturn(true);

        wordService.deleteById(id);

        verify(wordRepository).deleteById(id);

        when(wordRepository.findById(id)).thenReturn(Optional.empty());

        assertFalse(wordRepository.findById(id).isPresent());

    }

    @Test
    public void whenDeleteById_andWordDoesNotExistById_thenThrowsException() {

        long id = WordDataProvider.VALID_WORD_ID;
        when(wordRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> wordService.deleteById(id));
        verify(wordRepository, never()).deleteById(id);

    }

    @Test
    public void whenSave_andDifficultyIdDoesNotExist_thenThrowsException() {

        var wordRequest = WordDataProvider.VALID_WORD_REQUEST;
        Word word = WordDataProvider.WORD_ENTITY;

        when(difficultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordLengthGreaterThanDifficulty_thenThrowsException() {

        Difficulty difficulty = WordDataProvider.DIFFICULTY_ENTITY;
        Word word = WordDataProvider.WORD_ENTITY;
        var wordRequest = WordDataProvider.WORD_REQUEST_LONG_VALUE;

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));

        assertThrows(WordLengthGreaterThanDifficultyException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordAlreadyExists_thenThrowsException() {

        Difficulty difficulty = WordDataProvider.DIFFICULTY_ENTITY;
        Word word = WordDataProvider.WORD_ENTITY;
        var wordRequest = WordDataProvider.VALID_WORD_REQUEST;

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));
        when(wordRepository.existsByValue(word.getValue())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordDoesNotExist_andIsValid_thenSuccess() {

        var difficulty = WordDataProvider.DIFFICULTY_ENTITY;
        var wordRequest = WordDataProvider.VALID_WORD_REQUEST;
        var expectedEntity = WordDataProvider.WORD_ENTITY;

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));
        when(wordRepository.existsByValue(wordRequest.getValue())).thenReturn(false);

        Word wordToSave = Word.builder()
                .value(wordRequest.getValue())
                .difficulty(difficulty)
                .build();

        when(wordRepository.save(wordToSave)).thenReturn(expectedEntity);

        var expectedDTO = mapper.entityToDTO(expectedEntity);
        var actual = wordService.save(wordRequest);

        assertEquals(expectedDTO, actual);
        verify(wordRepository).existsByValue(wordRequest.getValue());
        verify(wordRepository).save(wordToSave);

    }

}